CREATE TABLE BOARD_COMMENTS (
                                COMMENT_SEQ INT PRIMARY KEY,
                                BOARD_SEQ INT,
                                USER_SEQ VARCHAR(10),
                                COMMENT_CONTENTS VARCHAR(5000) NOT NULL,
                                COMMENT_REG_ID VARCHAR(10) NOT NULL,
                                COMMENT_REG_DT DATE NOT NULL,
                                COMMENT_CHG_ID VARCHAR(10) NOT NULL,
                                COMMENT_CHG_DT DATE NOT NULL,
                                FOREIGN KEY (BOARD_SEQ) REFERENCES BOARD(BOARD_SEQ),
                                FOREIGN KEY (USER_SEQ) REFERENCES USER(USER_SEQ)
)