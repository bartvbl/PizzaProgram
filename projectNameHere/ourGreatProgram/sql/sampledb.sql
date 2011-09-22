USE PizzaBase;

#--Customer values
INSERT INTO Customer VALUES (NULL, 'Jack', 'Sparrow', 'Harald Gilles veg', '1', 7052 'Trondheim', 56729572, NULL);
INSERT INTO Customer VALUES (NULL, 'The', 'Engineer', 'O. S. Bragstads Plass', '2E', 7034 'Trondheim', 38562047, NULL);
INSERT INTO Customer VALUES (NULL, 'Sherlock', 'Holmes', 'Konrad Dahls Vei', '22', 7024 'Trondheim', 50174506, NULL);
INSERT INTO Customer VALUES (NULL, 'Commander', 'Shepard', 'Aunevegen', '10', 7021 'Trondheim', 92548936, NULL);
INSERT INTO Customer VALUES (NULL, 'Dr.', 'Troll', 'Tryms Veg', '8', 7033 'Trondheim', 83670186, NULL);
INSERT INTO Customer VALUES (NULL, 'The', 'Emperor', 'Ole L. Kolstads veg', '17', 7088 'Trondheim', 20672860, NULL);
INSERT INTO Customer VALUES (NULL, 'Pepper', 'Roni', 'Paul Røstads veg', '9', 7039 'Trondheim', 60257305, NULL);
INSERT INTO Customer VALUES (NULL, 'Steve', 'Jobs', 'Sunnlandsvegen', '16', 7032 'Trondheim', 60125183, NULL);
INSERT INTO Customer VALUES (NULL, 'Wall', 'E', 'Klosterenget', '6', 7030 'Trondheim', 20572919, NULL);
INSERT INTO Customer VALUES (NULL, 'Gordon', 'Freeman', 'Ada Arnfinsens veg', '18b', 7036 'Trondheim', 89353926, NULL);

#--Order values
INSERT INTO Order VALUES (NULL, 3, '2011-09-22 12:44:51', 'registered', 'pickupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 2, '2011-09-23 11:46:33', 'registered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 1, '2011-09-22 8:31:22', 'being cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 7, '2011-09-22 19:36:01', 'being cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 5, '2011-09-23 10:33:29', 'has been cooked', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 4, '2011-09-22 01:22:46', 'has been cooked', 'pickupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 8, '2011-09-22 12:44:10', 'being delivered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 10, '2011-09-23 19:25:27', 'being delivered', 'deliverAtHome', NULL);
INSERT INTO Order VALUES (NULL, 6, '2011-09-22 12:57:32', 'delivered', 'picupAtRestaurant', NULL);
INSERT INTO Order VALUES (NULL, 9, '2011-09-23 07:33:56', 'delivered', 'deliverAtHome', NULL);

