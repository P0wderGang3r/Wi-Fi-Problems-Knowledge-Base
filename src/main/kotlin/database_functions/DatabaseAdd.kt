package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByAttributeClass
import malfunctions

/**
 * Добавление новой неисправности
 */
fun addMalfunction(malfunctionName: String): Boolean {
    if (malfunctionName == "")
        return false

    //Добавляем, если нет такой неисправности
    if (findMalfunction(malfunctionName) == null) {
        malfunctions.add(MalfunctionClass(0, malfunctionName))
        return true
    }

    return false
}

/**
 * Добавление нового признака
 */
fun addAttribute(attributeName: String): Boolean {
    if (attributeName == "")
        return false

    if (findAttribute(attributeName) == null) {
        attributes.add(AttributeClass(0, attributeName))
        return true
    }

    return false
}

/**
 * Добавление возможного значения
 */
fun addAvailableValue(attributeName: String, value: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    //Если значение уже существует, то отказ
    if (findValue(value, attribute.availableValues)) {
        return false
    }

    attribute.availableValues.add(value)

    //Ограничение - если нормального значения не существует,
    // то добавляем в нормальные значения возможные значения
    if (attribute.normalValues.size == 0)
        attribute.normalValues.add(value)

    return true
}

/**
 * Добавление нормального значения
 */
fun addNormalValue(attributeName: String, value: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    //Если значение не из множества возможных значений, то отказ
    if (!findValue(value, attribute.availableValues))
        return false

    //Если значение уже существует, то отказ
    if (findValue(value, attribute.normalValues))
        return false


    attribute.normalValues.add(value)
    return true
}

/**
 * Добавление нового признака в признаки при неисправности
 */
fun addAttributePicture(malfunctionName: String, attributeName: String): Boolean {
    if (malfunctionName == "" ||attributeName == "")
        return false

    val malfunction = findMalfunction(malfunctionName) ?: return false
    val attribute = findAttribute(attributeName) ?: return false

    var picture = findAttributePicture(malfunctionName)

    //Если набор признаков при неисправности не существует, то добавляем
    if (picture == null) {
        picture = AttributePictureClass(malfunction)
        attributePictures.add(picture)
    }

    //Если признаковая картина не редактируема, то отказ
    if (!picture.isEditable)
        return false

    val attributeInPicture = findAttributeInPicture(picture, attributeName)

    //Если признак в признаках при неисправности не существует, то добавляем
    if (attributeInPicture == null) {
        picture.valuesByAttributes.add(ValuesByAttributeClass(attribute))
        return true
    }

    return false
}

/**
 * Заглушка
 */
fun addValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): Boolean {
    return true
}