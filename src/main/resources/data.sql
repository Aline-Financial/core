/* Populate Database with base data */
INSERT IGNORE INTO bank (id, address, city, state, zipcode, routing_number)
VALUES (1, '123 Aline Financial St.', 'New York', 'New York', '10001', '123456789');

INSERT IGNORE INTO branch (id, name, phone, address, city, state, zipcode, bank_id)
VALUES (1, 'Main Branch', '(800) 123-4567', '123 Aline Financial St.', 'New York', 'New York', '10001', 1);

/*********************************************************************
******************* USAGE WITH YOUR PROJECT **************************
**********************************************************************
 */

/*********************************************************************
*   Make sure to include the following property in your
*   application.yml / application.properties file:
*
*   *Properties*
*   spring.datasource.initialization-mode=always
*
*   *YAML*
*   spring:
*       datasource:
*           initialization-mode=always
**********************************************************************
*   This will make sure your project will initialize the database
*   if it has not already been initialized.
**********************************************************************
 */

