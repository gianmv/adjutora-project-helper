-- If update this schema name, update validation in AdjutoraUtils, this name
-- is use to validate if database is already created
CREATE SCHEMA U_ADJUTORA;

CREATE TABLE U_ADJUTORA.TASK
(
    TASKID       INTEGER                             NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TITLE        VARCHAR(250)                        NOT NULL,
    DESCRIPTION  VARCHAR(3000),
    CREATIONDATE TIMESTAMP DEFAULT current_timestamp NOT NULL,
    DUEDATE      TIMESTAMP,
    PARENTTASKID INTEGER,
    CONSTRAINT TASK_PK PRIMARY KEY (TASKID),
    CONSTRAINT TASK_TASK_FK FOREIGN KEY (PARENTTASKID) REFERENCES U_ADJUTORA.TASK (TASKID)
);

ALTER TABLE U_ADJUTORA.TASK
    ADD DONEDATE TIMESTAMP;


CREATE TABLE U_ADJUTORA.ADDITIONALINFO
(
    TASKID INTEGER      NOT NULL,
    NAME   VARCHAR(500) NOT NULL,
    VALUE  VARCHAR(5000),
    CONSTRAINT ADDITIONALINFO_TASK_FK FOREIGN KEY (TASKID) REFERENCES U_ADJUTORA.TASK (TASKID)
);

