-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 28, 2024 at 05:35 AM
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
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `ic_no`, `age`, `department`, `name`, `phone_no`, `address`, `email`) VALUES
(1, '880201-13-1233', 36, 'Psycology', 'Wong Siew Hong', '013-3333-3333', '12 Jalan Tunku Audul Rahman, 50100 Kuala Lumpur, Malaysia', 'wsh@gmail.com'),
(2, '870725-13-2334', 37, 'Cardiology', 'Siti Burhaliza', '012-323-2333', '21 Jalan Tun Razak, 50400 Kuala Lumpur, Malaysia', 'bur@email.com'),
(3, '920412-13-2341', 32, 'Pediatrics', 'Muhammad Faisal', '017-454-7656', '58 Jalan Sultan Ismail, 50250 Kuala Lumpur, Malaysia', 'faisal@gmail.com');

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
(26, '2023-01-26', 'Hypertension', '2023-02-05', 'Advised dietart changes, recommended regualr exercise', 2, 1),
(27, '2024-06-26', 'Angina Pectoris', '2024-07-15', 'Advised to avoid strenous activities', 2, 1),
(28, '2024-06-15', 'Major Depressive Disorder, moderate severity', '2024-07-15', 'Started cognitive-behavioral therapy session weekly', 1, 27);

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
(1, '1B, Jalan Merah, 96000 Sibu, Sarawak', '2002-03-03', 'andrew@email.com', 'Andrew Wong Zhi Hao', '012-343-2342'),
(6, 'Jalan Merdeka 12/4, Taman Seri Bayu, 43000 Kajang, Selangor', '1999-10-05', 'aziz@gmail.com', 'Amirul Aziz', '013-232-6742'),
(25, '25 Jalan Bukit Bintang, 55100 Kuala Lumpur, Malaysia', '1987-10-08', 'wangLi@gmail.com', 'Wang Li', '014-643-5633'),
(26, '42 Jalan Raja Chulan, 50200 Kuala Lumpur, Malaysia', '1991-07-25', 'meiLing@gmail.com', 'Tan Mei Ling', '014-457-8433'),
(27, '34 Lorong Damai, 10400 George Town, Penang, Malaysia', '1990-05-02', 'aisyah@gmail.com', 'Nur Aisyah', '017-533-6432');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

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
