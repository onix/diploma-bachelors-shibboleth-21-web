INSERT INTO stego_acs.acs_offices ("idOffice", name, address) VALUES
    (1, 'Kharkiv HQ', 'Ukraine, Kharkiv, Sums''ka str., 69'),
    (2, 'Dnipro office', 'Ukraine, Dnipropetrovs''k, Barykadna str. 50'),
    (3, 'Palo Alto', 'USA, CA, Palo Alto, Alma St 38');
ALTER SEQUENCE stego_acs."acs_offices_idOffice_seq" START WITH 4;
ALTER SEQUENCE stego_acs."acs_offices_idOffice_seq" RESTART;