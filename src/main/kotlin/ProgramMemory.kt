import data_classes.AttributeClass
import data_classes.AttributePictureClass
import data_classes.DiagnosticsClass
import data_classes.MalfunctionClass
import errors.ErrorClass

val malfunctions: ArrayList<MalfunctionClass> = ArrayList()

val attributes: ArrayList<AttributeClass> = ArrayList()

val attributePictures: ArrayList<AttributePictureClass> = ArrayList()

val diagnostics: ArrayList<DiagnosticsClass> = ArrayList()

var lastError: ErrorClass = ErrorClass.NULL