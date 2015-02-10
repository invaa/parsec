package com.parsec.downloader;

import java.io.IOException;

/**
 * Downloader contract.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */
public interface Downloader {
    void download(int start, int amount) throws InterruptedException, IOException;
}
