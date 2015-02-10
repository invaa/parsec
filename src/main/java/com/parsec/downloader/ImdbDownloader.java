package com.parsec.downloader;

import com.parsec.Settings;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Url downloader.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */
@Component
public class ImdbDownloader implements Downloader {

    private static final Logger logger = Logger.getLogger(ImdbDownloader.class);

    public void stringToFile( String text, String fileName ) throws IOException {
        Files.write(Paths.get(fileName), text.getBytes());
    }

    public String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData).append("\n");
        }
        reader.close();
        return fileData.toString();
    }

    public String readUrlAsString(String link) throws IOException {
        URL oracle = new URL(link);
        StringBuffer fileData = new StringBuffer();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            fileData.append(inputLine).append("\n");

        in.close();

        return fileData.toString();
    }

    public LinkedList<String> generateIds(int start, int amount) {

        assert start >= 0 && start + amount <= 9999999;

        LinkedList<String> list = new LinkedList<>();

        for (int i = start; i < amount + start && amount + start <= 9999999; i++) {
            list.add(String.format("tt%07d", i));
        }

        return list;
    }

    public void randomPause() throws InterruptedException {
        Random rnd = new Random();
        long delay = Settings.MIN_DELAY + rnd.nextInt(Settings.MAX_DELAY - Settings.MIN_DELAY);
        Thread.sleep(delay);
    }

    @Override
    public void download(int start, int amount) throws InterruptedException, IOException {
        LinkedList<String> ids = generateIds(start, amount);
        Collections.shuffle(ids);

        for (String id : ids) {
            randomPause();
            String location = Settings.OUTPUT_DIRECTORY + "/" + id + "_main";
            String fullcreditsLocation = Settings.OUTPUT_DIRECTORY + "/" + id + "_fullcredits";
            String parentalguideLocation = Settings.OUTPUT_DIRECTORY + "/" + id + "_parentalguide";
            String releaseinfoLocation = Settings.OUTPUT_DIRECTORY + "/" + id + "_releaseinfo";
            stringToFile(readUrlAsString("http://www.imdb.com/title/" + id), location);
            stringToFile(readUrlAsString("http://www.imdb.com/title/" + id + "/fullcredits"), fullcreditsLocation);
            stringToFile(readUrlAsString("http://www.imdb.com/title/" + id + "/parentalguide"), parentalguideLocation);
            stringToFile(readUrlAsString("http://www.imdb.com/title/" + id + "/releaseinfo"), releaseinfoLocation);

            logger.info("Successfully saved to: " + location);
        }
    }
}
