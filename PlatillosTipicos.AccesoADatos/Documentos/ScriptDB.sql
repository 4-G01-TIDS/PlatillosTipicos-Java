USE [master]
GO
  IF DB_ID('Platillos_tipicos') IS NOT NULL
BEGIN
    ALTER DATABASE [Platillos_tipicos] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE [Platillos_tipicos];
END

CREATE DATABASE [Platillos_tipicos];

GO
  USE [Platillos_tipicos];

GO
  --Crear la tabla de user
CREATE TABLE [dbo].[Users] (
    [Id] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID() NOT NULL,
    [Name] [varchar](50) NOT NULL,
    [LastName] [varchar](50) NULL,
    [ImgUser] [varbinary](max) NULL,
    [Email] [varchar](50) UNIQUE NOT NULL,
    [EmailConfirmed] [bit] NOT NULL DEFAULT 0,
	[EmailConfirmationToken][varchar](255) NULL,
    [Password] [char](32) NOT NULL,
    [IsCustomer] [bit] NOT NULL DEFAULT 0,
    [CreationDate] [datetime] NOT NULL DEFAULT GETDATE(),
    [IsNative] [bit] NOT NULL DEFAULT 0,
    [Nationality] [varchar](50) NULL,
    [Dui] [varchar](50) NULL,
    [DuiConfirmed] [bit] NOT NULL DEFAULT 0,
    [Membership] [bit] NOT NULL DEFAULT 0,
    [Age] [int] NULL,
    [PhoneNumber] [varchar](20) NULL,
    [PhoneNumberConfirmed] [bit] NOT NULL DEFAULT 0,
);

INSERT INTO Users (Id,Name, Email,EmailConfirmed, Password) VALUES
('23366d79-f322-4448-9e55-1518fc0c34b0','Admin','admin@gmail.com',1,'c07147eccb32ce3001ec189330557c98')--password: Qwerty.123!



--Crear la tabla de roles
CREATE TABLE [dbo].[Roles] (
    [RoleID] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID() NOT NULL,
    [RoleName] [nvarchar](50) NOT NULL
);

INSERT INTO Roles (RoleID, RoleName) VALUES
    ('7C49EFCE-847A-4A48-94E3-541827A68A9F', 'Administrator'),
    ('5A5D56EF-3655-4EB2-8B05-71C354B49C9F', 'Customer'),
    ('E6115E5F-1771-428F-AE39-9879FDD0C5CE', 'User');

--Crear la relacion entre Usuarios y roles
CREATE TABLE [dbo].[UserRoles] (
    [UserID] [uniqueidentifier] NOT NULL,
    [RoleID] [uniqueidentifier] NOT NULL DEFAULT 'E6115E5F-1771-428F-AE39-9879FDD0C5CE',
    CONSTRAINT [PK_UserRoles] PRIMARY KEY (UserID, RoleID),
    CONSTRAINT [FK1_UserRoles_Users] FOREIGN KEY (UserID) REFERENCES Users (Id) ON DELETE CASCADE,
    CONSTRAINT [FK2_UserRoles_Roles] FOREIGN KEY (RoleID) REFERENCES Roles (RoleID)
);


INSERT INTO UserRoles (UserID, RoleID) VALUES
('23366d79-f322-4448-9e55-1518fc0c34b0','7C49EFCE-847A-4A48-94E3-541827A68A9F')


--Crear tabla de departament
CREATE TABLE [dbo].[Departments] (
  [Id] [int] PRIMARY KEY NOT NULL,
  [Name] [varchar](50) NOT NULL,
  [Description] [varchar](800) NULL,
  [Latitude] [decimal](9, 6) NOT NULL,
  [Longitude] [decimal](9, 6) NOT NULL,
  [ImagePath] [varchar](50) NULL
);
-- Crear tabla de Municipios
CREATE TABLE [dbo].[Municipalities] (
  [Id] [int] PRIMARY KEY NOT NULL,
  [Name] [varchar](50) NOT NULL,
  [DepartmentId] [int]  NULL,
  CONSTRAINT [FK1_Municipalities_Departments] FOREIGN KEY (DepartmentId) REFERENCES [dbo].[Departments](Id)
);

	--Crear tabla de RestaurantImages
CREATE TABLE [dbo].[RestaurantImages](
  [Id] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
  [ImageRestaurant_menu] [varbinary](MAX) NULL,
  [ImageRestaurant_principal] [varbinary](MAX) NULL,
  [ImageRestaurant_1] [varbinary](MAX) NULL,
  [ImageRestaurant_2] [varbinary](MAX) NULL,
  [ImageRestaurant_3] [varbinary](MAX) NULL,
)
INSERT INTO RestaurantImages (Id) VALUES ('0a37f2b0-ee1f-11ed-a05b-0242ac120003');

	--Crear tabla de Restaurant-
CREATE TABLE [dbo].[Restaurants] (
  [Id] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
  [Name] [varchar](100) NOT NULL,
  [Description] [varchar](500) NULL,
  [Latitude] [decimal](9, 6) NOT NULL,
  [Longitude] [decimal](9, 6) NOT NULL,
  [PhoneNumber] [varchar](20) NULL,
  [PhoneNumberConfirmed] [bit] NOT NULL DEFAULT 0,
  [Website] [varchar](100) NULL,
  [Instagram] [varchar](100) NULL,
  [Facebook] [varchar](100) NULL,
  [Whatsapp] [varchar](20) NULL,
  [IsApproved] [bit] NOT NULL DEFAULT 0,
  [CreationDate] [datetime] NOT NULL DEFAULT GETDATE(),
  [UserId][uniqueidentifier] NOT NULL,
  [MunicipalityId][int] NOT NULL,
  [RestaurantImagesId][uniqueidentifier] NOT NULL ,
   CONSTRAINT [FK1_Restaurants_Users] FOREIGN KEY (UserId) REFERENCES [dbo].[Users](Id),
   CONSTRAINT [FK2_Restaurants_Municipalities] FOREIGN KEY (MunicipalityId) REFERENCES [dbo].[Municipalities](Id),
   CONSTRAINT [FK3_Restaurants_RestaurantImages] FOREIGN KEY (RestaurantImagesId) REFERENCES [dbo].[RestaurantImages](Id),
);


--Crear tabla de PublicationImages
CREATE TABLE [dbo].[PublicationImages](
[Id][uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
[ImagePublication1][varbinary](max) NULL,
[ImagePublication2][varbinary](max) NULL,
[ImagePublication3][varbinary](max) NULL,
[ImagePublication4][varbinary](max) NULL,
[ImagePublication5][varbinary](max) NULL,
);


--Crear tabla de Publications
CREATE TABLE [dbo].[Publications](
	[Id][uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
	[Repost][bit] NOT NULL DEFAULT 0,
	[Description][varchar](500) NULL,
	[PublicationDate][datetime] NOT NULL DEFAULT GETDATE(),
	[UserId][uniqueidentifier] NOT NULL,
	[PublicationImagesId][uniqueidentifier] NULL,
	[RestaurantId][uniqueidentifier] NULL,
	FOREIGN KEY (RestaurantId) REFERENCES Restaurants(Id) ON DELETE CASCADE,
	CONSTRAINT FK1_Publicacion_User FOREIGN KEY (UserId) REFERENCES Users(Id),
	CONSTRAINT FK2_Publication_PublicationImages FOREIGN KEY (PublicationImagesId) REFERENCES PublicationImages(Id) ON DELETE CASCADE
);

--Crear tabla de Likes de publicacion
CREATE TABLE [dbo].[PublicationLikes](
[Id][uniqueidentifier] NOT NULL DEFAULT NEWID(),
[IsLike][bit] NOT NULL,
[CreateDate][datetime] NOT NULL,
[UserId][uniqueidentifier] NOT NULL,
[PublicationId][uniqueidentifier] NOT NULL,
FOREIGN KEY (UserId) REFERENCES Users(Id) ON DELETE CASCADE,
FOREIGN KEY (PublicationId) REFERENCES Publications(Id) ON DELETE CASCADE
);

--Crear tabla de Coments
CREATE TABLE [dbo].[Comments](
[Id][uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
[Content][varchar](400) NOT NULL,
[CreateDate][datetime] NOT NULL,	
[Reports][int] NOT NULL DEFAULT 0,
[UserId][uniqueidentifier] NOT NULL,
[PublicationId][uniqueidentifier] NOT NULL,
CONSTRAINT FK1_Coments_User FOREIGN KEY (UserId) REFERENCES Users(Id),
CONSTRAINT FK2_Coments_Publication FOREIGN KEY (PublicationId) REFERENCES Publications(Id) ON DELETE CASCADE
);


--Crear tabla de Likes de comentarios
CREATE TABLE [dbo].[CommentLikes](
[Id][uniqueidentifier] NOT NULL DEFAULT NEWID(),
[IsLike][bit] NOT NULL,
[CreateDate][datetime] NOT NULL,
[UserId][uniqueidentifier] NOT NULL,
[CommentId][uniqueidentifier] NOT NULL,
FOREIGN KEY (UserId) REFERENCES Users(Id) ON DELETE CASCADE,
FOREIGN KEY (CommentId) REFERENCES Comments(Id) ON DELETE CASCADE
);


CREATE TABLE [dbo].[Notifications](
	[Id] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID(),
	[NotificationType] [varchar](50) NOT NULL,
	[Message] [varchar](500) NOT NULL,
	[NotificationDate] [datetime] NOT NULL DEFAULT GETDATE(),
	[IsRead] [bit] NOT NULL DEFAULT 0,
	[UserId] [uniqueidentifier] NOT NULL,
	[PublicationId] [uniqueidentifier] NOT NULL,
	CONSTRAINT [FK_Notifications_Users] FOREIGN KEY ([UserId]) REFERENCES [dbo].[Users] ([Id]),
	CONSTRAINT [FK_Notifications_Publications] FOREIGN KEY ([PublicationId]) REFERENCES [dbo].[Publications] ([Id]) ON DELETE CASCADE
);


--SECCION DE INSERTS
INSERT INTO Departments (Id, Name, Latitude, Longitude)
VALUES
  (1,'Ahuachapán', 13.919062, -89.845543),
  (2, 'Cabañas', 13.905189, -88.924702),
  (3, 'Chalatenango', 14.077337, -88.946555),
  (4, 'Cuscatlán', 13.821292, -89.018669),
  (5, 'La Libertad', 13.517296, -89.319532),
  (6, 'La Paz', 13.510359, -88.866537),
  (7, 'La Unión', 13.334448, -87.840413),
  (8, 'Morazán', 13.766165, -88.136479),
  (9, 'San Miguel', 13.484360, -88.178962),
  (10, 'San Salvador', 13.710808, -89.202777),
  (11, 'Santa Ana', 13.994900, -89.561064),
  (12, 'San Vicente', 13.641225, -88.787968),
  (13, 'Sonsonate', 13.719642, -89.725480),
  (14, 'Usulután', 13.385829, -88.466303)

  INSERT INTO Municipalities(Id, Name, DepartmentId) VALUES
(1, 'Ahuachapan', 1),
(2, 'Apaneca', 1),  
(3, 'Atiquizaya', 1),
(4, 'Concepcion de Ataco', 1),
(5, 'El Refugio', 1),
(6, 'Guaymango', 1),
(7, 'Jujutla', 1),
(8, 'San Francisco Menéndez', 1),
(9, 'San Lorenzo', 1),
(10, 'San Pedro Puxtla', 1),
(11, 'Tacuba', 1),
(12, 'Turín', 1),
(13, 'Sensuntepeque', 2), 
(14, 'Cinquera', 2), 
(15, 'Dolores', 2), 
(16, 'Guacotecti', 2), 
(17, 'Ilobasco', 2), 
(18, 'Jutiapa', 2), 
(19, 'San Isidro', 2), 
(20, 'Tejutepeque', 2), 
(21, 'Victoria', 2), 
(22, 'Chalatenango', 3), 
(23, 'Agua Caliente', 3), 
(24, 'Arcatao', 3), 
(25, 'Azacualpa', 3), 
(26, 'Cancasque', 3), 
(27, 'Citalá', 3), 
(28, 'Comalapa', 3), 
(29, 'Concepción Quezaltepeque', 3), 
(30, 'Dulce Nombre de María', 3), 
(31, 'El Carrizal', 3), 
(32, 'El Paraíso', 3), 
(33, 'La Laguna', 3), 
(34, 'La Palma', 3), 
(35, 'La Reina', 3), 
(36, 'Las Flores', 3), 
(37, 'Las Vueltas', 3), 
(38, 'Nombre de Jesús', 3), 
(39, 'Nueva Concepción', 3), 
(40, 'Nueva Trinidad', 3), 
(41, 'Ojos de Agua', 3), 
(42, 'Potonico', 3), 
(43, 'San Antonio de la Cruz', 3), 
(44, 'San Antonio Los Ranchos', 3), 
(45, 'San Fernando', 3), 
(46, 'San Francisco Lempa', 3), 
(47, 'San Francisco Morazán', 3), 
(48, 'San Ignacio', 3), 
(49, 'San Isidro Labrador', 3), 
(50, 'San Luis del Carmen', 3), 
(51, 'San Miguel de Mercedes', 3), 
(52, 'San Rafael', 3), 
(53, 'Santa Rita', 3), 
(54, 'Tejutla', 3),
(55, 'Cojutepeque', 4),
(56, 'Candelaria', 4),
(57, 'El Carmen', 4),
(58, 'El Rosario', 4),
(59, 'Monte San Juan', 4),
(60, 'Oratorio de Concepción', 4),
(61, 'San Bartolomé Perulapía', 4),
(62, 'San Cristóbal', 4),
(63, 'San José Guayabal', 4),
(64, 'San Pedro Perulapán', 4),
(65, 'San Rafael Cedros', 4),
(66, 'San Ramón', 4),
(67, 'Santa Cruz Analquito', 4),
(68, 'Santa Cruz Michapa', 4),
(69, 'Suchitoto', 4),
(70, 'Tenancingo', 4),
(71, 'Santa Tecla', 5),
(72, 'Antiguo Cuscatlán', 5),
(73, 'Chiltiupán', 5),
(74, 'Ciudad Arce', 5),
(75, 'Colón', 5),
(76, 'Comasagua', 5),
(77, 'Huizúcar', 5),
(78, 'Jayaque', 5),
(79, 'Jicalapa', 5),
(80, 'La Libertad', 5),
(81, 'Nuevo Cuscatlán', 5),
(82, 'Quezaltepeque', 5),
(83, 'San Juan Opico', 5),
(84, 'Sacacoyo', 5),
(85, 'San José Villanueva', 5),
(86, 'San Matías', 5),
(87, 'San Pablo Tacachico', 5),
(88, 'Talnique', 5),
(89, 'Tamanique', 5),
(90, 'Teotepeque', 5),
(91, 'Tepecoyo', 5),
(92, 'Zaragoza', 5),
(93, 'Zacatecoluca', 6),
(94, 'Cuyultitán', 6),
(95, 'El Rosario', 6),
(96, 'Jerusalén', 6),
(97, 'Mercedes La Ceiba', 6),
(98, 'Olocuilta', 6),
(99, 'Paraíso de Osorio', 6),
(100, 'San Antonio Masahuat', 6),
(101, 'San Emigdio', 6),
(102, 'San Francisco Chinameca', 6),
(103, 'San Pedro Masahuat', 6),
(104, 'San Juan Nonualco', 6),
(105, 'San Juan Talpa', 6),
(106, 'San Juan Tepezontes', 6),
(107, 'San Luis La Herradura', 6),
(108, 'San Luis Talpa', 6),
(109, 'San Miguel Tepezontes', 6),
(110, 'San Pedro Nonualco', 6),
(111, 'San Rafael Obrajuelo', 6),
(112, 'Santa María Ostuma', 6),
(113, 'Santiago Nonualco', 6),
(114, 'Tapalhuaca', 6),
(115, 'La Unión', 7),
(116, 'Anamorós', 7),
(117, 'Bolívar', 7),
(118, 'Concepción de Oriente', 7),
(119, 'Conchagua', 7),
(120, 'El Carmen', 7),
(121, 'El Sauce', 7),
(122, 'Intipucá', 7),
(123, 'Lislique', 7),
(124, 'Meanguera del Golfo', 7),
(125, 'Nueva Esparta', 7),
(126, 'Pasaquina', 7),
(127, 'Polorós', 7),
(128, 'San Alejo', 7),
(129, 'San José', 7),
(130, 'Santa Rosa de Lima', 7),
(131, 'Yayantique', 7),
(132, 'Yucuaiquín', 7),
(133, 'San Francisco Gotera', 8),
(134, 'Arambala', 8),
(135, 'Cacaopera', 8),
(136, 'Chilanga', 8),
(137, 'Corinto', 8),
(138, 'Delicias de Concepción', 8),
(139, 'El Divisadero', 8),
(140, 'El Rosario', 8),
(141, 'Gualococti', 8),
(142, 'Guatajiagua', 8),
(143, 'Joateca', 8),
(144, 'Jocoaitique', 8),
(145, 'Jocoro', 8),
(146, 'Lolotiquillo', 8),
(147, 'Meanguera', 8),
(148, 'Osicala', 8),
(149, 'Perquín', 8),
(150, 'San Carlos', 8),
(151, 'San Fernando', 8),
(152, 'San Isidro', 8),
(153, 'San Simón', 8),
(154, 'Sensembra', 8),
(155, 'Sociedad', 8),
(156, 'Torola', 8),
(157, 'Yamabal', 8),
(158, 'Yoloaiquín', 8),
(159, 'San Miguel', 9),
(160, 'Carolina', 9),
(161, 'Chapeltique', 9),
(162, 'Chinameca', 9),
(163, 'Chirilagua', 9),
(164, 'Ciudad Barrios', 9),
(165, 'Comacarán', 9),
(166, 'El Tránsito', 9),
(167, 'Lolotique', 9),
(168, 'Moncagua', 9),
(169, 'Nueva Guadalupe', 9),
(170, 'Nuevo Edén de San Juan', 9),
(171, 'Quelepa', 9),
(172, 'San Antonio', 9),
(173, 'San Gerardo', 9),
(174, 'San Jorge', 9),
(175, 'San Luis de la Reina', 9),
(176,'San Rafael Oriente', 9),
(177, 'Sesori', 9),
(178, 'Uluazapa', 9),
(179, 'San Salvador', 10),
(180, 'Aguilares', 10),
(181, 'Apopa', 10),
(182, 'Ayutuxtepeque', 10),
(183, 'Ciudad Delgado', 10),
(184, 'Cuscatancingo', 10),
(185, 'El Paisnal', 10),
(186, 'Guazapa', 10),
(187, 'Ilopango', 10),
(188, 'Mejicanos', 10),
(189, 'Nejapa', 10),
(190, 'Panchimalco', 10),
(191, 'Rosario de Mora', 10),
(192, 'San Marcos', 10),
(193, 'San Martín', 10),
(194, 'Santiago Texacuangos', 10),
(195, 'Santo Tomás', 10),
(196, 'Soyapango', 10),
(197, 'Tonacatepeque', 10),
(198, 'Santa Ana', 11),
(199, 'Candelaria de la Frontera', 11),
(200, 'Chalchuapa', 11),
(201, 'Coatepeque', 11),
(202, 'El Congo', 11),
(203, 'El Porvenir', 11),
(204, 'Masahuat', 11),
(205, 'Metapán', 11),
(206, 'San Antonio Pajonal', 11),
(207, 'San Sebastián Salitrillo', 11),
(208, 'Santa Rosa Guachipilín', 11),
(209, 'Santiago de la Frontera', 11),
(210, 'Texistepeque', 11),
(211, 'San Vicente', 12),
(212, 'Apastepeque', 12),
(213, 'Guadalupe', 12),
(214, 'San Cayetano Istepeque', 12),
(215, 'San Esteban Catarina', 12),
(216, 'San Ildefonso', 12),
(217, 'San Lorenzo', 12),
(218, 'San Sebastián', 12),
(219, 'Santa Clara', 12),
(220, 'Santo Domingo', 12),
(221, 'Tecoluca', 12),
(222, 'Tepetitán', 12),
(223, 'Nueva Esparta', 12),
(224, 'Sonsonate', 13),
(225, 'Acajutla', 13),
(226, 'Armenia', 13),
(227, 'Caluco', 13),
(228, 'Cuisnahuat', 13),
(229, 'Izalco', 13),
(230, 'Juayúa', 13),
(231, 'Nahuizalco', 13),
(232, 'Nahulingo', 13),
(233, 'Salcoatitán', 13),
(234, 'San Antonio del Monte', 13),
(235, 'San Julián', 13),
(236, 'Santa Catarina Masahuat', 13),
(237, 'Santa Isabel Ishuatán', 13),
(238, 'Santo Domingo de Guzmán', 13),
(239, 'SSonzacate', 13),
(240, 'Usulután', 14),
(241, 'Alegría', 14),
(242, 'Berlín', 14),
(243, 'California', 14),
(244, 'Concepción Batres', 14),
(245, 'El Triunfo', 14),
(246, 'Ereguayquín', 14),
(247, 'Estanzuelas', 14),
(248, 'Jiquilisco', 14),
(249, 'Jucuapa', 14),
(250, 'Jucuarán', 14),
(251, 'Mercedes Umaña', 14),
(252, 'Nueva Granada', 14),
(253, 'Ozatlán', 14),
(254, 'Puerto El Triunfo', 14),
(255, 'San Agustín', 14),
(256, 'San Buenaventura', 14),
(257, 'San Dionisio', 14),
(258, 'San Francisco Javier', 14),
(259, 'Santa Elena', 14),
(260, 'Santa María', 14),
(261, 'Santiago de María', 14),
(262, 'Tecapán', 14);	

INSERT INTO Restaurants(Name, Latitude, Longitude, RestaurantImagesId, UserId, MunicipalityId) VALUES
('RestaurantInicial', 13.732685, -89.373997, '0a37f2b0-ee1f-11ed-a05b-0242ac120003', '23366d79-f322-4448-9e55-1518fc0c34b0', 75
);