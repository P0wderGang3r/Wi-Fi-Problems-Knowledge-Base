package database_functions

import attributePictures
import attributes
import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.MalfunctionClass
import data_classes.ValuesByAttributeClass
import malfunctions
import java.io.FileReader

/**
 * Считывание текста из файла
 */
fun initText(fileName: String): String {
    val reader: FileReader
    var lText = ""

    try {
        reader = FileReader(fileName)
    } catch (e: java.lang.Exception) {
        return "Error"
    }

    var inp: Int = reader.read()

    while (inp != -1) {
        lText = lText.plus(inp.toChar())
        inp = reader.read()
    }
    return lText
}

/**
 * Разбиение прочитанного текста на набор строк
 */
fun initArrayOfText(fileName: String): ArrayList<String> {
    val text = initText(fileName)

    val lineList = text.split("\n\r", "\n", "\r") as java.util.ArrayList<String>

    lineList.remove("")

    return lineList
}

/**
 * Разбиение набора строк на первичный набор аргументов
 */
fun initInputStructure(fileName: String): ArrayList<List<String>> {
    val lineList = initArrayOfText(fileName)

    val inputStructure: ArrayList<List<String>> = ArrayList()

    for (line in lineList) {
        inputStructure.add(line.split(" : "))
    }

    return inputStructure
}

/**
 * Считывание множества неисправностей
 */
fun initMalfunctionClasses(path: String): Boolean {
    val rawMalfunctions = initInputStructure(path + "malfunctions.txt")

    for (malfunction in rawMalfunctions) {
        malfunctions.add(MalfunctionClass(Integer.parseInt(malfunction[0]), malfunction[1]))
    }

    return true
}

/**
 * Считывание множества признаков
 */
fun initAttributeClasses(path: String): Boolean {
    val rawAttributes = initInputStructure(path + "attributes.txt")

    for (attribute in rawAttributes) {
        attributes.add(AttributeClass(Integer.parseInt(attribute[0]), attribute[1]))
    }

    return true
}

/**
 * Считывание множества возможных значений
 */
fun initAvailableValueClasses(path: String): Boolean {
    val rawValues = initInputStructure(path + "available_values.txt")

    for (value in rawValues) {
        for (attribute in attributes) {
            if (attribute.number == Integer.parseInt(value[0])) {
                attribute.availableValues.add(value[1])
            }
        }
    }

    return true
}

/**
 * Считывание множества нормальных значений
 */
fun initNormalValueClasses(path: String): Boolean {
    val rawValues = initInputStructure(path + "normal_values.txt")

    for (value in rawValues) {
        for (attribute in attributes) {
            if (attribute.number == Integer.parseInt(value[0])) {
                attribute.normalValues.add(value[1])
            }
        }
    }

    return true
}

/**
 * Считывание множества признаков при неисправности
 */
fun initAttributePicture(path: String): Boolean {
    val rawPictures = initInputStructure(path + "attribute_picture.txt")

    for (picture in rawPictures) {
        val attributesInPicture = picture[1].split(" ; ")

        val numOfMalfunction = Integer.parseInt(picture[0])

        for (malfunction in malfunctions) {
            if (malfunction.number == numOfMalfunction) {
                attributePictures.add(AttributePictureClass(malfunction))
            }
        }

        for (attributeNum in attributesInPicture) {
            for (attribute in attributes) {
                if (attribute.number == Integer.parseInt(attributeNum)) {
                    attributePictures[attributePictures.size - 1]
                        .valuesByAttributes.add(ValuesByAttributeClass(attribute))
                }
            }
        }
    }

    return true
}

/**
 * Считывание множества значений признаков при неисправности
 */
fun initValuesByMalfunction(path: String): Boolean {
    val rawValues = initInputStructure(path + "values_by_malfunction.txt")

    for (valueByMalfunction in rawValues) {
        val numOfMalfunction = Integer.parseInt(valueByMalfunction[0])
        val numOfAttribute = Integer.parseInt(valueByMalfunction[1])

        for (attributePicture in attributePictures) {
            if (attributePicture.malfunction.number == numOfMalfunction) {

                for (attributeInPicture in attributePicture.valuesByAttributes) {
                    if (attributeInPicture.attribute.number == numOfAttribute) {

                        val values = valueByMalfunction[2].split(" ; ")

                        for (value in values) {
                            attributeInPicture.values.add(value)
                        }

                    }
                }

            }
        }

    }

    return true
}

/**
 * Привязка/создание картины признаков при исправной работе с нормальными значениями признаков
 */
fun initAttributePictureForNormalValues() {
    var currMalfunction = MalfunctionClass(0, "работает исправно")
    for (malfunction in malfunctions)
        if (malfunction.number == 0) {
            currMalfunction = malfunction
            break
        }

    attributePictures.add(AttributePictureClass(currMalfunction))
    attributePictures[attributePictures.size - 1].isEditable = false

    for (attribute in attributes)
        attributePictures[attributePictures.size - 1].valuesByAttributes.add(ValuesByAttributeClass(attribute))

    for (valuesByAttribute in attributePictures[attributePictures.size - 1].valuesByAttributes) {
        valuesByAttribute.values = valuesByAttribute.attribute.normalValues
    }
}

fun clearDataBase() {
    attributePictures.clear()
    malfunctions.clear()
    for (attribute in attributes) {
        attribute.availableValues.clear()
        attribute.normalValues.clear()
    }
    attributes.clear()
}

fun initDataBase(inpPath: String): Boolean {
    var path = inpPath

    if (path.isNotEmpty() && path[path.length - 1] != '/') {
        path += "/"
    }

    clearDataBase()

    try {
        if (!initMalfunctionClasses(path)) {
            println("Ошибка прочтения перечня неисправностей")
            return false
        }

        if (!initAttributeClasses(path)) {
            println("Ошибка прочтения перечня признаков")
            return false
        }

        if (!initAvailableValueClasses(path)) {
            println("Ошибка прочтения перечня областей допустимых значений")
            return false
        }

        if (!initNormalValueClasses(path)) {
            println("Ошибка прочтения перечня областей возможных значений")
            return false
        }

        initAttributePictureForNormalValues()

        if (!initAttributePicture(path)) {
            println("Ошибка прочтения перечня значимых признаков для неисправностей")
            return false
        }

        if (!initValuesByMalfunction(path)) {
            println("Ошибка прочтения перечня значений признаков при неисправности")
            return false
        }

    } catch (e: java.lang.Exception) {
        return false
    }

    return true
}