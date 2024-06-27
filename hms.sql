-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 27, 2024 at 11:48 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hms`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `id` int(11) NOT NULL,
  `patientID` int(11) NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `date` date NOT NULL,
  `doctorID` int(11) NOT NULL,
  `purpose` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`id`, `patientID`, `startTime`, `endTime`, `date`, `doctorID`, `purpose`) VALUES
(8, 1, '06:30:00', '08:00:00', '2024-06-18', 2, 'Checkup\n'),
(10, 6, '04:30:00', '06:00:00', '2024-07-07', 1, 'Recheck'),
(11, 1, '02:00:00', '04:00:00', '2024-06-27', 2, '-');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` int(11) NOT NULL,
  `ic_no` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `ic_no`, `age`, `department`, `name`, `phone_no`, `address`, `dob`, `email`, `phone`) VALUES
(1, '020410-13-1234', 30, 'Psycology', 'Muhammad', '013-3333-3333', NULL, NULL, NULL, NULL),
(2, '02232313', 45, 'NOdep', 'Sabi', '2312424242', NULL, NULL, NULL, NULL),
(3, '34234343c', 55, 'Haveddep', 'Anothersabi', '23224434', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `medical_records`
--

CREATE TABLE `medical_records` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `follow_up_date` date DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `doctor_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medical_records`
--

INSERT INTO `medical_records` (`id`, `date`, `diagnosis`, `follow_up_date`, `treatment`, `doctor_id`, `patient_id`) VALUES
(9, '2022-02-21', '3242', '2022-02-21', '32345', 1, 1),
(10, '2022-02-20', 'wd', NULL, 'wd', 1, 1),
(15, '1999-12-21', 'qwdwqdqwd', '1998-12-12', 'dwqdwqdwq', 3, 1),
(19, '2024-10-11', 'This is shabi sick', '2025-10-10', 'No treatment can be used', 3, 13);

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`id`, `address`, `dob`, `email`, `name`, `phone`) VALUES
(1, '1B, Jalan Merah, 96000 Sibu, Sarawak', '2022-03-03', 'andrew@email.com', 'Andredqwdw', '123-532-4322'),
(6, '1B, Jalan Melaka', '2003-08-01', 'Lily@email.com', 'Lily', '013-466-3443'),
(12, 'dwd', '1988-02-21', 'wdw', 'wee', '019-232-3212'),
(13, 'dd', '2002-10-10', 'dwdwd', 'eww', 'sdsd'),
(15, 'ads', '2020-10-10', 'sdsa', 'dwwdd', 'sadsad'),
(16, 'sd', '2024-12-12', 'ded', 'gregr', 'dwdw'),
(20, '112,test', '2020-01-01', 'test@example.com', 'test', '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `password`, `role`) VALUES
('9b4f9153-7e74-4208-bd13-ff85302ef260', 'test', '$2a$10$u/YZfX1jw6p3YMVdJhvlCO4hA6/hNS2cKARaGUCajyu.RY5mjF.w2', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `doctorID` (`doctorID`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medical_records`
--
ALTER TABLE `medical_records`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtny13k9v4o58styd47st3s2l5` (`doctor_id`),
  ADD KEY `FKrav12h9aiw7pegjt62p8owwn3` (`patient_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `medical_records`
--
ALTER TABLE `medical_records`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`doctorID`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`patientID`) REFERENCES `patients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `medical_records`
--
ALTER TABLE `medical_records`
  ADD CONSTRAINT `FKrav12h9aiw7pegjt62p8owwn3` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKtny13k9v4o58styd47st3s2l5` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
