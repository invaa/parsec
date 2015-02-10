package com.parsec.parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Parser contract.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */
public interface Parser {
    void parse(String urlAsString) throws ParserConfigurationException, XPathExpressionException;
    void parseAll() throws XPathExpressionException, ParserConfigurationException;
}
