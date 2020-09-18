-- DROP SCHEMA dbo;

CREATE SCHEMA dbo;
-- warehouse.dbo.allergies definition

-- Drop table

-- DROP TABLE warehouse.dbo.allergies GO

CREATE TABLE warehouse.dbo.allergies (
	[start] date NULL,
	stop date NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.careplans definition

-- Drop table

-- DROP TABLE warehouse.dbo.careplans GO

CREATE TABLE warehouse.dbo.careplans (
	Id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[start] date NULL,
	stop date NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reasoncode varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reasondescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.conditions definition

-- Drop table

-- DROP TABLE warehouse.dbo.conditions GO

CREATE TABLE warehouse.dbo.conditions (
	[start] date NULL,
	stop date NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.devices definition

-- Drop table

-- DROP TABLE warehouse.dbo.devices GO

CREATE TABLE warehouse.dbo.devices (
	[start] datetime NULL,
	stop datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	udi varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.encounters definition

-- Drop table

-- DROP TABLE warehouse.dbo.encounters GO

CREATE TABLE warehouse.dbo.encounters (
	Id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[start] datetime NULL,
	stop datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	organization varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	provider varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payer varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounterclass varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	base_encounter_cost float NULL,
	total_claim_cost float NULL,
	payer_coverage float NULL,
	reasoncode varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reasondescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.imagingdetails definition

-- Drop table

-- DROP TABLE warehouse.dbo.imagingdetails GO

CREATE TABLE warehouse.dbo.imagingdetails (
	imaging varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[path] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.imagingstudies definition

-- Drop table

-- DROP TABLE warehouse.dbo.imagingstudies GO

CREATE TABLE warehouse.dbo.imagingstudies (
	Id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[date] datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bodysite_code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bodysite_description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	modality_code varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	modality_description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sop_code varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sop_description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.immunizations definition

-- Drop table

-- DROP TABLE warehouse.dbo.immunizations GO

CREATE TABLE warehouse.dbo.immunizations (
	[date] datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	base_cost float NULL
) GO;


-- warehouse.dbo.medications definition

-- Drop table

-- DROP TABLE warehouse.dbo.medications GO

CREATE TABLE warehouse.dbo.medications (
	[start] datetime NULL,
	stop datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payer varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	base_cost float NULL,
	payer_coverage float NULL,
	dispenses int NULL,
	totalcost float NULL,
	reasoncode varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reasondescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.observations definition

-- Drop table

-- DROP TABLE warehouse.dbo.observations GO

CREATE TABLE warehouse.dbo.observations (
	[date] datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	value varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	units varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[type] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;


-- warehouse.dbo.patients definition

-- Drop table

-- DROP TABLE warehouse.dbo.patients GO

CREATE TABLE warehouse.dbo.patients (
	Id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birthdate date NULL,
	deathdate date NULL,
	ssn varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	drivers varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	passport varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prefix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[first] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[last] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	suffix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maiden varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	marital varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	race varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnicity varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	gender varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birthplace varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	address varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	city varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	state varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	country varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	zip varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lat varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lon varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	healthcare_expenses float NULL,
	healthcare_coverage float NULL
) GO;


-- warehouse.dbo.procedures definition

-- Drop table

-- DROP TABLE warehouse.dbo.procedures GO

CREATE TABLE warehouse.dbo.procedures (
	[date] datetime NULL,
	patient varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	encounter varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	code varchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	base_cost float NULL,
	reasoncode varchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reasondescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) GO;

