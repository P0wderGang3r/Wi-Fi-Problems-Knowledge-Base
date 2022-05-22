package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByAttributeClass
import errors.ErrorClass
import malfunctions

/**
 * Добавление новой неисправности
 */
fun addMalfunction(malfunctionName: String): ErrorClass {
    if (malfunctionName == "")
        return ErrorClass.ADD_DEFAULT

    //Добавляем, если нет такой неисправности
    if (findMalfunction(malfunctionName) == null) {
        malfunctions.add(MalfunctionClass(0, malfunctionName))
        return ErrorClass.NULL
    }

    return ErrorClass.ADD_DEFAULT
}

/**
 * Добавление нового признака
 */
fun addAttribute(attributeName: String): ErrorClass {
    if (attributeName == "")
        return ErrorClass.ADD_DEFAULT

    if (findAttribute(attributeName) == null) {
        attributes.add(AttributeClass(0, attributeName))
        return ErrorClass.NULL
    }

    return ErrorClass.ADD_DEFAULT
}

/**
 * Добавление возможного значения
 */
fun addAvailableValue(attributeName: String, value: String): ErrorClass {
    if (attributeName == "" || value == "")
        return ErrorClass.ADD_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.ADD_DEFAULT

    //Если значение уже существует, то отказ
    if (findValue(value, attribute.availableValues)) {
        return ErrorClass.ADD_DEFAULT
    }

    attribute.availableValues.add(value)

    //Ограничение - если нормального значения не существует,
    // то добавляем в нормальные значения возможные значения
    if (attribute.normalValues.size == 0)
        attribute.normalValues.add(value)

    return ErrorClass.NULL
}

/**
 * Добавление нормального значения
 */
fun addNormalValue(attributeName: String, value: String): ErrorClass {
    if (attributeName == "" || value == "")
        return ErrorClass.ADD_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.ADD_DEFAULT

    //Если значение не из множества возможных значений, то отказ
    if (!findValue(value, attribute.availableValues))
        return ErrorClass.ADD_DEFAULT

    //Если значение уже существует, то отказ
    if (findValue(value, attribute.normalValues))
        return ErrorClass.ADD_DEFAULT


    attribute.normalValues.add(value)
    return ErrorClass.NULL
}

/**
 * Добавление нового признака в признаки при неисправности
 */
fun addAttributePicture(malfunctionName: String, attributeName: String): ErrorClass {
    if (malfunctionName == "" ||attributeName == "")
        return ErrorClass.ADD_DEFAULT

    val malfunction = findMalfunction(malfunctionName) ?: return ErrorClass.ADD_DEFAULT
    val attribute = findAttribute(attributeName) ?: return ErrorClass.ADD_DEFAULT

    var picture = findAttributePicture(malfunctionName)

    //Если набор признаков при неисправности не существует, то добавляем
    if (picture == null) {
        picture = AttributePictureClass(malfunction)
        attributePictures.add(picture)
    }

    //Если признаковая картина не редактируема, то отказ
    if (!picture.isEditable)
        return ErrorClass.ADD_DEFAULT

    val attributeInPicture = findAttributeInPicture(picture, attributeName)

    //Если признак в признаках при неисправности не существует, то добавляем
    if (attributeInPicture == null) {
        picture.valuesByAttributes.add(ValuesByAttributeClass(attribute))
        return ErrorClass.NULL
    }

    return ErrorClass.ADD_DEFAULT
}

/**
 * Заглушка
 */
fun addValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): ErrorClass {
    return ErrorClass.NULL
}