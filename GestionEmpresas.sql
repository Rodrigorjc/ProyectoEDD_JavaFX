-- Esta Base de Datos tendra que tener el mismo nombre en cada uno de los dispositivos en el cual se vaya a ejecutar
-- El nombre en concreto es el siguiente: GestionEmpresas

-- Creación de la tabla Alumnos
CREATE TABLE Alumnos (
    CodigoAlumno INT AUTO_INCREMENT PRIMARY KEY,
    DNI CHAR(9) UNIQUE,
    Nombre VARCHAR(100),
    Apellidos VARCHAR(100),
    FechaNacimiento DATE,
    CONSTRAINT check_dni_length CHECK (LENGTH(DNI) = 9 AND DNI REGEXP '^[0-9]{8}[A-Za-z]$')
);

-- Creación de la tabla Tutores
CREATE TABLE Tutores (
    CodigoTutor INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellidos VARCHAR(100),
    CorreoElectronico VARCHAR(255),
    DNI CHAR(9) UNIQUE,
    Telefono VARCHAR(20),
    CONSTRAINT check_dni_length CHECK (LENGTH(DNI) = 9 AND DNI REGEXP '^[0-9]{8}[A-Za-z]$')
);

-- Creación de la tabla Empresas
CREATE TABLE Empresas (
    CodigoEmpresa INT AUTO_INCREMENT PRIMARY KEY,
    CIF VARCHAR(9) UNIQUE,
    RazonSocial VARCHAR(255),
    Direccion VARCHAR(255),
    CP VARCHAR(10),
    Localidad VARCHAR(255),
    TipoJornada ENUM('Partida', 'Continua'),
    Modalidad ENUM('Presencial', 'Semipresencial', 'Distancia'),
    MailEmpresa VARCHAR(255),
    Responsable INT,
    TutorLaboral INT,
    foreign key (Responsable) references Tutores(CodigoTutor),
    foreign key (TutorLaboral) references Tutores(CodigoTutor),
    CONSTRAINT check_cif_format CHECK (LENGTH(CIF) = 9 AND CIF REGEXP '^[A-Za-z][0-9]{8}$')
);
-- Creación de la tabla Asignados
CREATE TABLE Asignados (
    CodigoAsignacion INT AUTO_INCREMENT PRIMARY KEY,
    CodigoAlumno INT,
    CodigoEmpresa INT,
    CodigoTutor INT
    FechaHoraAsignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CodigoAlumno) REFERENCES Alumnos(CodigoAlumno),
    FOREIGN KEY (CodigoEmpresa) REFERENCES Empresas(CodigoEmpresa)
    foreign key (CodigoTutor) references Tutores(CodigoTutor),
);


DROP TABLE IF EXISTS Asignados;
DROP TABLE IF EXISTS Alumnos;
DROP TABLE IF EXISTS Empresas;
DROP TABLE IF EXISTS Tutores;



INSERT INTO Alumnos (DNI, Nombre, Apellidos, FechaNacimiento) 
VALUES ('12345678A', 'Juan', 'Pérez', '2000-01-01'),
       ('23456789B', 'María', 'González', '2001-02-02'),
       ('34567890C', 'Carlos', 'López', '1999-03-03');

INSERT INTO Tutores (Nombre, Apellidos, CorreoElectronico, DNI, Telefono)
VALUES ('Tutor1', 'Apellido', 'pedro@example.com', '12341278L', '153456789'),
('Tutor2', 'Apellido2', 'tutor2@example.com', '23456789B', '0987654321'),
('Tutor3', 'Apellido3', 'tutor3@example.com', '34567890C', '1122334455');

INSERT INTO Empresas (CIF, RazonSocial, Direccion, CP, Localidad, TipoJornada, Modalidad, MailEmpresa, Responsable, TutorLaboral)
VALUES ('A12345678', 'Empresa 1', 'Calle Principal 123', '28001', 'Madrid', 'Continua', 'Presencial', 'empresa1@example.com', 1, 8),
       ('B23456789', 'Empresa 2', 'Avenida Central 456', '08001', 'Barcelona', 'Partida', 'Semipresencial', 'empresa2@example.com', 8, 9),
       ('C34567890', 'Empresa 3', 'Plaza Principal 789', '41001', 'Sevilla', 'Continua', 'Distancia', 'empresa3@example.com', 10, 9);



