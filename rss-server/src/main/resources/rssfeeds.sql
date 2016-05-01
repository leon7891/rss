
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for feeds
-- ----------------------------
DROP TABLE IF EXISTS `feeds`;
CREATE TABLE `feeds` (`ID` int(11) NOT NULL AUTO_INCREMENT, `URL` varchar(200) NOT NULL, `TITLE` varchar(50) NOT NULL, `LAST_PUB_DATE` datetime DEFAULT NULL, `IMAGE` varchar(200) DEFAULT NULL, PRIMARY KEY (`ID`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for feed_items
-- ----------------------------
DROP TABLE IF EXISTS `feed_items`;
CREATE TABLE `feed_items` (`ID` int(11) NOT NULL AUTO_INCREMENT, `TITLE` varchar(50) NOT NULL, `URL` varchar(200) DEFAULT NULL, `PUB_DATE` datetime DEFAULT NULL, `FEED_ID` int(11) NOT NULL, `IMAGE` varchar(200) DEFAULT NULL, PRIMARY KEY (`ID`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subscriptions
-- ----------------------------
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions` ( `ID` int(11) NOT NULL AUTO_INCREMENT, `USER_ID` int(11) NOT NULL, `FEED_ID` int(11) NOT NULL, PRIMARY KEY (`ID`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` ( `ID` int(11) NOT NULL AUTO_INCREMENT, `NAME` varchar(50) NOT NULL, `PASSWORD` varchar(100) NOT NULL, `LAST_PUB_DATE` datetime DEFAULT NULL, PRIMARY KEY (`ID`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT INTO USERS (NAME, PASSWORD) VALUES ('admin', '123456');
INSERT INTO USERS (NAME, PASSWORD) VALUES ('user', 'test');

INSERT INTO FEEDS (URL, TITLE, IMAGE) VALUES ('http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml', 'NYT > Home Page', 'https://static01.nyt.com/images/misc/NYT_logo_rss_250x40.png');
INSERT INTO FEEDS (URL, TITLE, IMAGE) VALUES ('http://feeds.bbci.co.uk/news/rss.xml', 'BBC News - Home', 'http://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif');