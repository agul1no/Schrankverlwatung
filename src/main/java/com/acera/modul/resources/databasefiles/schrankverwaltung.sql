-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 23. Nov 2022 um 11:09
-- Server-Version: 10.4.25-MariaDB
-- PHP-Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `schrankverwaltung`
--
CREATE DATABASE IF NOT EXISTS `schrankverwaltung` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `schrankverwaltung`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `garments`
--

CREATE TABLE `garments` (
  `pk_garment_id` int(11) NOT NULL,
  `garment_type` varchar(30) NOT NULL,
  `garment_brand` varchar(30) NOT NULL,
  `garment_price` double NOT NULL,
  `garment_color` varchar(30) NOT NULL,
  `garment_date_purchase` bigint(20) NOT NULL,
  `fk_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `garments`
--

INSERT INTO `garments` (`pk_garment_id`, `garment_type`, `garment_brand`, `garment_price`, `garment_color`, `garment_date_purchase`, `fk_user_id`) VALUES
(2, 'Hose', 'Adidas', 50, 'schwarz', 1668034800000, 1),
(3, 'Tshirt', 'Nike', 22, 'blau', 1631224800000, 1),
(31, 'Tshirt', 'Iridaily', 30, 'rot', 1663711200000, 5),
(32, 'Shorts', 'Artengo', 8, 'schwarz', 1644966000000, 5),
(33, 'Tshirt', 'Adidas', 33.5, 'grau', 1668639600000, 5),
(34, 'Schuhe', 'Vans', 80, 'schwarz', 1668726000000, 5),
(37, 'Mütze', 'Nike', 10, 'grau', 1667948400000, 5),
(38, 'Krawatte', 'Boss', 100, 'schwarz', 1668726000000, 5),
(42, 'Pullover', 'Iridaily', 80, 'schwarz', 1641423600000, 1),
(43, 'Socken', 'Nike', 14, 'weiß', 1666216800000, 1),
(44, 'Shorts', 'Artengo', 30, 'schwarz', 1658354400000, 1),
(45, 'Tshirt', 'H&M', 10, 'weiß', 1595368800000, 1),
(48, 'leggins', 'zara', 10, 'leopard', 1666821600000, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `pk_user_id` int(11) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`pk_user_id`, `user_name`, `user_password`) VALUES
(1, 'Augusto', 'd6135a3af5efd56d7aa0dea57b8ed1ea344d8f0a540cdc334b9158c9c0e6ff8b'),
(5, 'Agu', 'f6f2ea8f45d8a057c9566a33f99474da2e5c6a6604d736121650e2730c6fb0a3');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `garments`
--
ALTER TABLE `garments`
  ADD PRIMARY KEY (`pk_garment_id`),
  ADD KEY `garment_to_user` (`fk_user_id`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`pk_user_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `garments`
--
ALTER TABLE `garments`
  MODIFY `pk_garment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `pk_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `garments`
--
ALTER TABLE `garments`
  ADD CONSTRAINT `garment_to_user` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`pk_user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
