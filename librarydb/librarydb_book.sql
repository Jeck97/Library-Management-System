-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: librarydb
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bookID` int NOT NULL AUTO_INCREMENT,
  `bookName` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `dataPublished` date DEFAULT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `loanRate` double NOT NULL,
  `availability` tinyint NOT NULL,
  `synopsis` varchar(2000) NOT NULL,
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Hamlet ( Folger Library Shakespeare)','William Shakespeare ','1997-07-01','Classic',1.5,1,'Hamlet is Shakespeare\'s most popular, and most puzzling, play. It follows the form of a \"revenge tragedy,\" in which the hero, Hamlet, seeks vengeance against his father\'s murderer, his uncle Claudius, now the king of Denmark. Much of its fascination, however, lies in its uncertainties.'),(2,'Swann Way In Search of Lost Time (Penguin Classics Deluxe Edition)','Marcel Proust','2004-11-30','Adventure',1,1,'Marcel Proust’s In Search of Lost Time is one of the most entertaining reading experiences in any language and arguably the finest novel of the twentieth century. But since its original prewar translation there has been no completely new version in English. Now, Penguin Classics brings Proust’s masterpiece to new audiences throughout the world, beginning with Lydia Davis’s internationally acclaimed translation of the first volume, Swann’s Way.'),(3,'Moby Dick (Wordsworth Classics)','Moby Dick (Wordsworth Classics) ','1999-12-05','Horror',1.5,1,'With an Introduction and Notes by David Herd. Lecturer in English and American Literature at the University of Kent at Canterbury Moby-Dick is the story of Captain Ahab\'s quest to avenge the whale that \'reaped\' his leg. The quest is an obsession and the novel is a diabolical study of how a man becomes a fanatic. But it is also a hymn to democracy. Bent as the crew is on Ahab s appalling crusade, it is equally the image of a co-operative community at work: all hands dependent on all hands, each individual responsible for the security of each. Among the crew is Ishmael, the novel\'s narrator, ordinary sailor, and extraordinary reader. Digressive, allusive, vulgar, transcendent, the story Ishmael tells is above all an education: in the practice of whaling, in the art of writing.'),(4,'Stories of Anton Chekhov','Anton Chekhov ','2000-10-31','Classic',1,1,'Richard Pevear and Larissa Volokhonsky, the highly acclaimed translators of War and Peace, Doctor Zhivago, and Anna Karenina, which was an Oprah Book Club pick and million-copy bestseller, bring their unmatched talents to The Selected Stories of Anton Chekhov, a collection of thirty of Chekhov’s best tales from the major periods of his creative life.'),(5,'The Adventures of Huckleberry Finn (Amazon Classics Edition)','Mark Twain','2015-07-25','Adventure',1.5,1,'Garnished with the details that Mark Twain gathered during his own travels up and down the lush and changeable Mississippi River, The Adventures of Huckleberry Finn provides an unparalleled glimpse into the pre-Civil War South as runaway Huck Finn – a white boy – teams up with fugitive adult slave Jim as they flee by raft on the river. Long one of the most challenged or banned books due to racist language, Twain’s novel can be read as an indictment of unenlightened nineteenth-century thinking or as a heartbreaking coming-of-age novel, but what’s undisputed is the novel’s position as one of the most influential books in American literature.'),(6,'Middlemarch (Penguin Classics)','George Eliot ','2003-03-25','Adventure',1,1,'George Eliot’s novel, Middlemarch: A Study of Provincial Life, explores a fictional nineteenth-century Midlands town in the midst of modern changes. The proposed Reform Bill promises political change; the building of railroads alters both the physical and cultural landscape; new scientific approaches to medicine incite public division; and scandal lurks behind respectability. The quiet drama of ordinary lives and flawed choices are played out in the complexly portrayed central characters of the novel—the idealistic Dorothea Brooke; the ambitious Dr. Lydgate; the spendthrift Fred Vincy; and the steadfast Mary Garth. The appearance of two outsiders further disrupts the town’s equilibrium—Will Ladislaw, the spirited nephew of Dorothea’s husband, the Rev. Edward Casaubon, and the sinister John Raffles, who threatens to expose the hidden past of one of the town’s elite. Middlemarch displays George Eliot’s clear-eyed yet humane understanding of characters caught up in the mysterious unfolding of self-knowledge. This Penguin Classics edition uses the second edition of 1874 and features an introduction and notes by Eliot-biographer Rosemary Ashton. In her introduction, Ashton discusses themes of social change in Middlemarch, and examines the novel as an imaginative embodiment of Eliot\'s humanist beliefs.'),(7,'Lolita','Vladimir Nabokov ','1989-03-13','Romantic',0.5,1,'Awe and exhiliration--along with heartbreak and mordant wit--abound in Lolita, Nabokov\'s most famous and controversial novel, which tells the story of the aging Humbert Humbert\'s obsessive, devouring, and doomed passion for the nymphet Dolores Haze. Lolita is also the story of a hypercivilized European colliding with the cheerful barbarism of postwar America. Most of all, it is a meditation on love--love as outrage and hallucination, madness and transformation.'),(8,'The Great Gatsby','F. Scott Fitzgerald','2004-09-30','Adventure',1.5,1,'A true classic of twentieth-century literature, this edition has been updated by Fitzgerald scholar James L.W. West III to include the author’s final revisions and features a note on the composition and text, a personal foreword by Fitzgerald’s granddaughter, Eleanor Lanahan—and a new introduction by two-time National Book Award winner Jesmyn Ward.'),(9,'War and Peace (Vintage Classics)','Leo Tolstoy','2008-12-02','War',1,1,'From the award-winning translators of Anna Karenina and The Brothers Karamazov comes this magnificent new translation of Tolstoy\'s masterwork.'),(10,'Madame Bovary (Vintage Classics)','Gustave Flaubert ','1991-12-14','Romantic',1.5,1,'For daring to peer into the heart of an adulteress and enumerate its contents with profound dispassion, the author of Madame Bovary was tried for \"offenses against morality and religion.\" What shocks us today about Flaubert\'s devastatingly realized tale of a young woman destroyed by the reckless pursuit of her romantic dreams is its pure artistry: the poise of its narrative structure, the opulence of its prose (marvelously captured in the English translation of Francis Steegmuller), and its creation of a world whose minor figures are as vital as its doomed heroine. In reading Madame Bovary, one experiences a work that remains genuinely revolutionary almost a century and a half after its creation.');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-14 17:08:28
