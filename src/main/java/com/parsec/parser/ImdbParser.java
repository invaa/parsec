package com.parsec.parser;

import com.parsec.Settings;
import com.parsec.model.CastCrew;
import com.parsec.model.Genre;
import com.parsec.model.Movie;
import com.parsec.repository.MovieRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for downloaded pages. Produces entities.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */

@Component
public class ImdbParser implements Parser {

    private static final Logger logger = Logger.getLogger(ImdbParser.class);

    @Autowired
    private MovieRepository movieRepository;

    //TODO: need to be refactored and optimized
    @Override
    public void parse(String urlAsString) throws ParserConfigurationException, XPathExpressionException {
        if (urlAsString == null) {
            //illegal argument
            return;
        }

        TagNode tagNode = new HtmlCleaner().clean(urlAsString);
        org.w3c.dom.Document doc = new DomSerializer(
                new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        String linkWithId = ((String) xpath.evaluate("//link[@rel='canonical']/@href", doc, XPathConstants.STRING));
        String id = "";

        Pattern pattern = Pattern.compile("tt[0-9]+");
        Matcher matcher = pattern.matcher(linkWithId);
        if (matcher.find()){
            id = matcher.group();
        }

        //get movie by id
        if (StringUtils.isBlank(id)) {
            logger.debug("Id is not fount on page " + urlAsString.substring(0, urlAsString.length() > 100 ? 100 : urlAsString.length()) + " ... , stopped.");
            return;
        }

        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            //new movie
            movie = new Movie(id);
        }

        String title = (String) xpath.evaluate("//h1[@class='header']/span[@itemprop='name' and @class='itemprop']", doc, XPathConstants.STRING);

        String rating = (String) xpath.evaluate("//span[@itemprop='ratingValue']", doc, XPathConstants.STRING);

        String description = (String) xpath.evaluate("//p[@itemprop='description']", doc, XPathConstants.STRING);

        String img = (String) xpath.evaluate("//img[@itemprop='image' and @src[contains(., 'V1_SX214_AL')]]/@src", doc, XPathConstants.STRING);

        String runtime = ((String) xpath.evaluate("//time[@itemprop='duration']", doc, XPathConstants.STRING))
                .replace("\n"," ").replaceAll("( )+", " ").trim();

        String budget = ((String) xpath.evaluate("//div[@class='txt-block' and ./h4[contains(.,'Budget')]]", doc, XPathConstants.STRING))
                .replace((String) xpath.evaluate("//div[@class='txt-block' and ./h4[contains(.,'Budget')]]/h4", doc, XPathConstants.STRING), "")
                .replace((String) xpath.evaluate("//div[@class='txt-block' and ./h4[contains(.,'Budget')]]/span", doc, XPathConstants.STRING), "")
                .replace("\n"," ").replace("&nbsp;", " ").replaceAll("( )+", " ").trim();

        List<Genre> genres = new ArrayList<>();
        XPathExpression expr = xpath.compile("//div[@itemprop='genre']/a[contains(@href, 'genre')]/text()");

        NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (StringUtils.isNotEmpty(node.getTextContent())) {
                genres.add(new Genre(node.getTextContent()));
            }
        }

        expr = xpath.compile("//table[@id='release_dates']/tbody/tr");
        list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        String releaseDate = "";

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (StringUtils.isNotEmpty(node.getTextContent())) {
                if ("USA".equalsIgnoreCase(((String) xpath.evaluate("//tr/td", node, XPathConstants.STRING))) || StringUtils.isEmpty(releaseDate)) {
                    releaseDate = ((String) xpath.evaluate("//tr/td/text()", node, XPathConstants.STRING));
                }
            }
        }

        expr = xpath.compile("//div[@id='fullcredits_content'][h4 or table]/*[self::h4 or self::table]");

        list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        String castLine = "";

        LinkedList<CastCrew> castAndCrew = new LinkedList<>();

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);

            if (i % 2 == 0) {
                castLine = node.getTextContent().replace("\n"," ").replace("&nbsp;"," ").replaceAll("( )+", " ").trim();
            } else {
                String name = "";
                String role = "";

                boolean hasARole = false;
                boolean isARole = false;

                NodeList subList = (NodeList) xpath.evaluate("./tbody/tr/*", node, XPathConstants.NODESET);
                for (int j = 0; j < subList.getLength(); j++) {
                    Node subNode = subList.item(j);
                    String subNodeText = subNode.getTextContent().replace("\n", "").trim();
                    //using length to avoid space symbol look-a-likes
                    boolean isNotEmpty = StringUtils.isNotEmpty(subNodeText) && subNodeText.length() > 1;

                    if (isARole && isNotEmpty) {
                        role = subNodeText.replaceAll("( )+", " ");
                        castAndCrew.add(new CastCrew(name, role, castLine, movie));
                        isARole = false;
                        continue;
                    }

                    if ("...".equals(subNodeText)) {
                        hasARole = true;
                        isARole = true;
                    }

                    if (!isARole && isNotEmpty) {
                        name = subNodeText;
                    }
                }

                if (!hasARole) {
                    //set role as castLine
                    role = castLine.replace("\n"," ").replaceAll("( )+", " ").trim();
                    castAndCrew.add(new CastCrew(name, role, castLine, movie));
                }
            }
        }

        String parentalGuide = (String) xpath.evaluate("//a[contains(@href, '/search/title?certificates=us')]/text()", doc, XPathConstants.STRING);

        //update fields that are not empty;
        if (StringUtils.isNotBlank(title)) {
            movie.setTitle(title);
        }
        if (StringUtils.isNotBlank(rating)) {
            movie.setRating(rating);
        }
        if (StringUtils.isNotBlank(description)) {
            movie.setDescription(description);
        }
        if (StringUtils.isNotBlank(img)) {
            movie.setPoster(img);
        }
        if (StringUtils.isNotBlank(runtime)) {
            movie.setRuntime(runtime);
        }
        if (StringUtils.isNotBlank(budget)) {
            movie.setBudget(budget);
        }
        if (StringUtils.isNotBlank(releaseDate)) {
            movie.setReleaseDate(releaseDate);
        }
        if (StringUtils.isNotBlank(parentalGuide)) {
            movie.setParentalGuide(parentalGuide);
        }
        if (genres.size() > 0) {
            movie.setGenres(genres);
        }
        if (castAndCrew.size() > 0) {
            movie.setCastAndCrew(castAndCrew);
        }
        movieRepository.save(movie);
    }

    @Override
    public void parseAll() throws XPathExpressionException, ParserConfigurationException {
        logger.info("parseAll started at: " + (new Date()));

        long readFileTimeCounter = 0L;
        long parseTimeCounter = 0L;

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(Settings.OUTPUT_DIRECTORY))) {
            for (Path path : directoryStream) {
                long currentTime = new Date().getTime();
                if (Files.isRegularFile(path)) {
                    String fileAsString = new String(Files.readAllBytes(Paths.get(path.toString())));
                    readFileTimeCounter += new Date().getTime() - currentTime;

                    currentTime = new Date().getTime();
                    parse(fileAsString);
                    parseTimeCounter += new Date().getTime() - currentTime;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        logger.info("I/O time: " + readFileTimeCounter);
        logger.info("Parse and save time: " + parseTimeCounter);
        logger.info("parseAll ended at: " + (new Date()));
    }
}
