package com.parsec;

/**
 * Main runner and controller.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */

import com.parsec.downloader.Downloader;
import com.parsec.model.Movie;
import com.parsec.parser.Parser;
import com.parsec.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@EnableAutoConfiguration
@ImportResource("classpath:app-context.xml")
public class ServerMain {

    @Autowired
    Parser parser;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    Downloader downloader;

    @RequestMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.getWriter().println("<a href='/showall'>Show all</a>" +
                "<br/>" +
                "<a href='/download?id='>Download by id</a>" +
                "<br/>" +
                "<a href='/show?id='>Show by id</a>" +
                "<br/>" +
                "<a href='/showbytitle?title='>Show by title</a>" +
                "<br/>" +
                "<a href='/parseall'>Parse all</a>");
    }

    @RequestMapping("/massload")
    public boolean download(@RequestParam(value = "start", required = true) int start,
                            @RequestParam(value = "amount", required = true) int amount) throws IOException, InterruptedException {
        downloader.download(start, amount);
        return true;
    }

    @RequestMapping("/download")
    public boolean download(@RequestParam(value = "id", required = true) String id) throws IOException, InterruptedException, XPathExpressionException, ParserConfigurationException {
        int idInt = Integer.valueOf(id.replace("tt", ""));

        downloader.download(idInt, 1);

        //TODO hardcoded for the demo, remove after demo
        parser.parse(new String(Files.readAllBytes(Paths.get(Settings.OUTPUT_DIRECTORY + "/" + id + "_main"))));
        parser.parse(new String(Files.readAllBytes(Paths.get(Settings.OUTPUT_DIRECTORY + "/" + id + "_fullcredits"))));
        parser.parse(new String(Files.readAllBytes(Paths.get(Settings.OUTPUT_DIRECTORY + "/" + id + "_parentalguide"))));
        parser.parse(new String(Files.readAllBytes(Paths.get(Settings.OUTPUT_DIRECTORY + "/" + id + "_releaseinfo"))));

        return true;
    }

    //TODO for testing purposes only, remove after demo
    @RequestMapping("/test250")
    public boolean test250() throws IOException, InterruptedException, XPathExpressionException, ParserConfigurationException {
        downloader.download(111161,1);
        downloader.download(68646,1);
        downloader.download(71562,1);
        downloader.download(468569,1);
        downloader.download(110912,1);
        downloader.download(50083,1);
        downloader.download(108052,1);
        downloader.download(60196,1);
        downloader.download(167260,1);
        downloader.download(137523,1);
        downloader.download(120737,1);
        downloader.download(80684,1);
        downloader.download(109830,1);
        downloader.download(1375666,1);
        downloader.download(73486,1);
        downloader.download(167261,1);
        downloader.download(99685,1);
        downloader.download(133093,1);
        downloader.download(76759,1);
        downloader.download(47478,1);
        downloader.download(816692,1);
        downloader.download(317248,1);
        downloader.download(114369,1);
        downloader.download(114814,1);
        downloader.download(102926,1);
        downloader.download(38650,1);
        downloader.download(64116,1);
        downloader.download(110413,1);
        downloader.download(118799,1);
        downloader.download(34583,1);
        downloader.download(82971,1);
        downloader.download(120586,1);
        downloader.download(120815,1);
        downloader.download(21749,1);
        downloader.download(245429,1);
        downloader.download(54215,1);
        downloader.download(47396,1);
        downloader.download(2582802,1);
        downloader.download(1675434,1);
        downloader.download(27977,1);
        downloader.download(103064,1);
        downloader.download(209144,1);
        downloader.download(120689,1);
        downloader.download(253474,1);
        downloader.download(407887,1);
        downloader.download(43014,1);
        downloader.download(78788,1);
        downloader.download(172495,1);
        downloader.download(57012,1);
        downloader.download(88763,1);
        downloader.download(78748,1);
        downloader.download(482571,1);
        downloader.download(32553,1);
        downloader.download(405094,1);
        downloader.download(110357,1);
        downloader.download(1853728,1);
        downloader.download(95765,1);
        downloader.download(1345836,1);
        downloader.download(81505,1);
        downloader.download(50825,1);
        downloader.download(169547,1);
        downloader.download(910970,1);
        downloader.download(53125,1);
        downloader.download(90605,1);
        downloader.download(33467,1);
        downloader.download(52357,1);
        downloader.download(211915,1);
        downloader.download(22100,1);
        downloader.download(95327,1);
        downloader.download(82096,1);
        downloader.download(364569,1);
        downloader.download(119698,1);
        downloader.download(435761,1);
        downloader.download(86190,1);
        downloader.download(87843,1);
        downloader.download(66921,1);
        downloader.download(105236,1);
        downloader.download(75314,1);
        downloader.download(112573,1);
        downloader.download(36775,1);
        downloader.download(180093,1);
        downloader.download(56592,1);
        downloader.download(51201,1);
        downloader.download(56172,1);
        downloader.download(338013,1);
        downloader.download(93058,1);
        downloader.download(45152,1);
        downloader.download(70735,1);
        downloader.download(40522,1);
        downloader.download(86879,1);
        downloader.download(71853,1);
        downloader.download(208092,1);
        downloader.download(62622,1);
        downloader.download(42876,1);
        downloader.download(59578,1);
        downloader.download(119488,1);
        downloader.download(12349,1);
        downloader.download(42192,1);
        downloader.download(53604,1);
        downloader.download(361748,1);
        downloader.download(53291,1);
        downloader.download(40897,1);
        downloader.download(97576,1);
        downloader.download(114709,1);
        downloader.download(41959,1);
        downloader.download(1832382,1);
        downloader.download(55630,1);
        downloader.download(372784,1);
        downloader.download(986264,1);
        downloader.download(17136,1);
        downloader.download(105695,1);
        downloader.download(86250,1);
        downloader.download(81398,1);
        downloader.download(1049413,1);
        downloader.download(1187043,1);
        downloader.download(71315,1);
        downloader.download(363163,1);
        downloader.download(57115,1);
        downloader.download(95016,1);
        downloader.download(2106476,1);
        downloader.download(47296,1);
        downloader.download(457430,1);
        downloader.download(31679,1);
        downloader.download(113277,1);
        downloader.download(50212,1);
        downloader.download(119217,1);
        downloader.download(96283,1);
        downloader.download(116231,1);
        downloader.download(50976,1);
        downloader.download(2267998,1);
        downloader.download(1280558,1);
        downloader.download(15864,1);
        downloader.download(44741,1);
        downloader.download(80678,1);
        downloader.download(89881,1);
        downloader.download(50986,1);
        downloader.download(993846,1);
        downloader.download(83658,1);
        downloader.download(17925,1);
        downloader.download(120735,1);
        downloader.download(1065073,1);
        downloader.download(1305806,1);
        downloader.download(112641,1);
        downloader.download(1205489,1);
        downloader.download(1291584,1);
        downloader.download(118715,1);
        downloader.download(434409,1);
        downloader.download(347149,1);
        downloader.download(32976,1);
        downloader.download(405508,1);
        downloader.download(77416,1);
        downloader.download(892769,1);
        downloader.download(61512,1);
        downloader.download(25316,1);
        downloader.download(55031,1);
        downloader.download(116282,1);
        downloader.download(117951,1);
        downloader.download(31381,1);
        downloader.download(758758,1);
        downloader.download(268978,1);
        downloader.download(1979320,1);
        downloader.download(33870,1);
        downloader.download(46912,1);
        downloader.download(2562232,1);
        downloader.download(167404,1);
        downloader.download(46268,1);
        downloader.download(395169,1);
        downloader.download(84787,1);
        downloader.download(266543,1);
        downloader.download(477348,1);
        downloader.download(978762,1);
        downloader.download(64115,1);
        downloader.download(266697,1);
        downloader.download(1255953,1);
        downloader.download(91763,1);
        downloader.download(79470,1);
        downloader.download(2024544,1);
        downloader.download(74958,1);
        downloader.download(52311,1);
        downloader.download(46911,1);
        downloader.download(292490,1);
        downloader.download(75686,1);
        downloader.download(469494,1);
        downloader.download(2015381,1);
        downloader.download(93779,1);
        downloader.download(92005,1);
        downloader.download(2278388,1);
        downloader.download(401792,1);
        downloader.download(52618,1);
        downloader.download(53198,1);
        downloader.download(107207,1);
        downloader.download(245712,1);
        downloader.download(405159,1);
        downloader.download(32551,1);
        downloader.download(1028532,1);
        downloader.download(60827,1);
        downloader.download(32138,1);
        downloader.download(36868,1);
        downloader.download(2084970,1);
        downloader.download(87544,1);
        downloader.download(848228,1);
        downloader.download(83987,1);
        downloader.download(440963,1);
        downloader.download(246578,1);
        downloader.download(44079,1);
        downloader.download(56801,1);
        downloader.download(1954470,1);
        downloader.download(1130884,1);
        downloader.download(338564,1);
        downloader.download(114746,1);
        downloader.download(169102,1);
        downloader.download(79944,1);
        downloader.download(73195,1);
        downloader.download(44706,1);
        downloader.download(112471,1);
        downloader.download(1877832,1);
        downloader.download(38787,1);
        downloader.download(88247,1);
        downloader.download(107048,1);
        downloader.download(1504320,1);
        downloader.download(1201607,1);
        downloader.download(1220719,1);
        downloader.download(83922,1);
        downloader.download(75148,1);
        downloader.download(48424,1);
        downloader.download(58946,1);
        downloader.download(198781,1);
        downloader.download(72890,1);
        downloader.download(113247,1);
        downloader.download(353969,1);
        downloader.download(325980,1);
        downloader.download(72684,1);
        downloader.download(61184,1);
        downloader.download(58461,1);
        downloader.download(47528,1);
        downloader.download(120382,1);
        downloader.download(92067,1);
        downloader.download(38355,1);
        downloader.download(1454029,1);
        downloader.download(107290,1);
        downloader.download(46250,1);
        downloader.download(61722,1);
        downloader.download(70511,1);
        downloader.download(54997,1);
        downloader.download(101414,1);
        downloader.download(1392214,1);
        downloader.download(118694,1);
        downloader.download(2338151,1);
        downloader.download(154420,1);
        downloader.download(40746,1);

        return true;
    }


    @RequestMapping("/showall")
    public List<Movie> showall() {
        return movieRepository.findAll();
    }

    @RequestMapping("/show")
    public Movie show(@RequestParam(value = "id", required = true) String id) {
        return movieRepository.findOne(id);
    }

    @RequestMapping("/showbytitle")
    public List<Movie> showbytitle(@RequestParam(value = "title", required = true) String title) {
        if (title.length() < 3) {
            throw new IllegalArgumentException("Title length should be at least 3");
        }

        return movieRepository.findByTitleContaining(title);
    }

    @RequestMapping("/parseall")
    public boolean parseall() {
        try {
            parser.parseAll();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerMain.class, args);
    }

}