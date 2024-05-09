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
    NombreApellido VARCHAR(200),
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
    FechaHoraAsignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CodigoAlumno) REFERENCES Alumnos(CodigoAlumno),
    FOREIGN KEY (CodigoEmpresa) REFERENCES Empresas(CodigoEmpresa)
);


DROP TABLE IF EXISTS Asignados;
DROP TABLE IF EXISTS Alumnos;
DROP TABLE IF EXISTS Empresas;
DROP TABLE IF EXISTS Tutores;
