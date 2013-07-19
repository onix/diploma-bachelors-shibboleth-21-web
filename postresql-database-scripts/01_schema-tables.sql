-- Schema: stego_acs
-- DROP SCHEMA stego_acs;
CREATE SCHEMA stego_acs;

CREATE SEQUENCE stego_acs."acs_admins_idAdmin_seq";
CREATE SEQUENCE stego_acs."acs_authLogTypeOfEntry_idType_seq";
CREATE SEQUENCE stego_acs."acs_authLog_idEntry_seq";
CREATE SEQUENCE stego_acs."acs_employees_idEmployee_seq";
CREATE SEQUENCE stego_acs."acs_offices_idOffice_seq";
CREATE SEQUENCE stego_acs."acs_officeEmployeeGrantedAccess_idAccess_seq";

-- Table: stego_acs.acs_auth_log_type_of_entry
-- DROP TABLE stego_acs.acs_auth_log_type_of_entry;
CREATE TABLE stego_acs.acs_auth_log_type_of_entry
(
  "idType" integer NOT NULL DEFAULT nextval('stego_acs."acs_authLogTypeOfEntry_idType_seq"'::regclass),
  name character varying(50) NOT NULL,
  description character varying(100) NOT NULL,
  CONSTRAINT "idTypePK" PRIMARY KEY ("idType"),
  CONSTRAINT "nameOptionUnique" UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_employees
-- DROP TABLE stego_acs.acs_employees;
CREATE TABLE stego_acs.acs_employees
(
  "idEmployee" integer NOT NULL DEFAULT nextval('stego_acs."acs_employees_idEmployee_seq"'::regclass),
  name character varying(100),
  surname character varying(100),
  "secondName" character varying(100),
  address character varying(500),
  phone character varying(50),
  email character varying(100),
  photo bytea,
  CONSTRAINT "idEmployeePK" PRIMARY KEY ("idEmployee"),
  CONSTRAINT "emailUnique" UNIQUE (email )
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_offices
-- DROP TABLE stego_acs.acs_offices;
CREATE TABLE stego_acs.acs_offices
(
  "idOffice" integer NOT NULL DEFAULT nextval('stego_acs."acs_offices_idOffice_seq"'::regclass),
  name character varying(200) NOT NULL,
  address character varying(500) NOT NULL, 
  CONSTRAINT "idOfficePK" PRIMARY KEY ("idOffice"),
  CONSTRAINT "nameOfficeUnique" UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_auth_log
-- DROP TABLE stego_acs.acs_auth_log
CREATE TABLE stego_acs.acs_auth_log
(
  "idEntry" integer NOT NULL DEFAULT nextval('stego_acs."acs_authLog_idEntry_seq"'::regclass),
  "officeId" integer NOT NULL,
  "typeOfEntryId" integer NOT NULL,
  "timeOfAction" TIMESTAMP NOT NULL,
  "employeeId" integer,
  CONSTRAINT "idEntryPK" PRIMARY KEY ("idEntry"),
  CONSTRAINT "officeIdFK" FOREIGN KEY ("officeId")
    REFERENCES stego_acs.acs_offices ("idOffice") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT "typeOfEntryIdFK" FOREIGN KEY ("typeOfEntryId")
    REFERENCES stego_acs.acs_auth_log_type_of_entry ("idType") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT "employeeIdFK" FOREIGN KEY ("employeeId")
    REFERENCES stego_acs.acs_employees ("idEmployee") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_pass_employee
-- DROP TABLE stego_acs.acs_pass_employee;
CREATE TABLE stego_acs.acs_pass_employee
(
  "idEmployee" integer NOT NULL,
  "isPassActive"  boolean NOT NULL DEFAULT 'true',
  "dateOfExpiration" TIMESTAMP,
  "authenticationId" bytea NOT NULL,
  CONSTRAINT "idEmployeePassPK" PRIMARY KEY ("idEmployee"),
  CONSTRAINT "authenticationIdUnique" UNIQUE ("authenticationId"),
  CONSTRAINT "idEmployeeFK" FOREIGN KEY ("idEmployee")
    REFERENCES stego_acs.acs_employees ("idEmployee") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_admins
-- DROP TABLE stego_acs.acs_admins;
CREATE TABLE stego_acs.acs_admins
(
  "idAdmin" integer NOT NULL DEFAULT nextval('stego_acs."acs_admins_idAdmin_seq"'::regclass),
  login character varying(50) NOT NULL,
  password character varying(128) NOT NULL,
  role character varying NOT NULL DEFAULT 'admin',
  enabled boolean NOT NULL DEFAULT 'true',
  "employeeAffilatedToAdmin" integer,
  CONSTRAINT "idAdminPK" PRIMARY KEY ("idAdmin"),
  CONSTRAINT "loginUnique" UNIQUE (login),
  CONSTRAINT "employeeAffilatedToAdminFK" FOREIGN KEY ("employeeAffilatedToAdmin")
      REFERENCES stego_acs.acs_employees ("idEmployee") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

-- Table: stego_acs.acs_office_employee_granted_access
-- DROP TABLE stego_acs.acs_office_employee_granted_access;
CREATE TABLE stego_acs.acs_office_employee_granted_access
(
  "idEmployeeToOfficeGrantedAccess" integer NOT NULL DEFAULT nextval('stego_acs."acs_officeEmployeeGrantedAccess_idAccess_seq"'::regclass),
  "employeeId" integer NOT NULL,
  "officeId" integer NOT NULL, 
  "timePeriodFrom" TIMESTAMP NOT NULL, 
  "timePeriodTo" TIMESTAMP, 
  CONSTRAINT "idEmployeeToOfficeGrantedAccessPK" PRIMARY KEY ("idEmployeeToOfficeGrantedAccess"),
  CONSTRAINT "employeeIdFK" FOREIGN KEY ("employeeId")
    REFERENCES stego_acs.acs_employees ("idEmployee") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT "officeIdFK" FOREIGN KEY ("officeId")
    REFERENCES stego_acs.acs_offices ("idOffice") MATCH SIMPLE
    ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
