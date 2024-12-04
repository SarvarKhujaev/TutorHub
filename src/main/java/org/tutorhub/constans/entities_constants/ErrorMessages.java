package org.tutorhub.constans.entities_constants;

import org.tutorhub.annotations.LinksToDocs;

@SuppressWarnings( value = "хранит сообщения об ошибках при работе с параметрами таблиц" )
@LinksToDocs( links = "https://www.geeksforgeeks.org/hibernate-validator-with-example/" )
public final class ErrorMessages {
    public static final String NULL_VALUE = "VALUE CANNOT BE EMPTY";

    public static final String WRONG_EMAIL = "WRONG EMAIL FORMAT WAS INSERTED";

    public static final String DATE_IS_INVALID = "YOUR DATE MUST BE PRESENT OR FUTURE";

    public static final String VALUE_OUT_OF_RANGE = "YOUR VALUE IS OUT OF RANGE";
}
