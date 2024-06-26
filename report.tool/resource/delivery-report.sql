-- amss.delivery_report definition

CREATE TABLE `delivery_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `originator` varchar(128) COLLATE latin1_german2_ci NOT NULL,
  `recipient` varchar(128) COLLATE latin1_german2_ci NOT NULL,
  `arrival_time` varchar(32) COLLATE latin1_german2_ci DEFAULT NULL,
  `non_delivery_reason` int(11) DEFAULT NULL,
  `non_delivery_diagnostic_code` int(11) DEFAULT NULL,
  `suplement_info` varchar(128) COLLATE latin1_german2_ci DEFAULT NULL,
  `subject_id` varchar(128) COLLATE latin1_german2_ci DEFAULT NULL,
  `content_id` varchar(128) COLLATE latin1_german2_ci DEFAULT NULL,
  `encode_information_type` varchar(128) COLLATE latin1_german2_ci DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_correlator_string` varchar(128) COLLATE latin1_german2_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_german2_ci;