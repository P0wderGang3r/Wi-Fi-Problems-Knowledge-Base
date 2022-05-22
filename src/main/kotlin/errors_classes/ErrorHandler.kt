package errors_classes

import errors_classes.error_handlers.IErrorHandler
import errors_classes.error_handlers.default_error_handlers.AddDefaultErrorHandler
import errors_classes.error_handlers.default_error_handlers.EditDefaultErrorHandler
import errors_classes.error_handlers.default_error_handlers.InitDefaultErrorHandler
import errors_classes.error_handlers.default_error_handlers.RemoveDefaultErrorHandler

enum class ErrorHandler {
    NULL {
        override fun getErrorHandler(): IErrorHandler? {
            return null
        }
    },


    ADD_DEFAULT {
        private val errorHandler: IErrorHandler = AddDefaultErrorHandler()

        override fun getErrorHandler(): IErrorHandler {
            return errorHandler
        }
    },


    EDIT_DEFAULT {
        private val errorHandler: IErrorHandler = EditDefaultErrorHandler()

        override fun getErrorHandler(): IErrorHandler {
            return errorHandler
        }
    },


    REMOVE_DEFAULT {
        private val errorHandler: IErrorHandler = RemoveDefaultErrorHandler()

        override fun getErrorHandler(): IErrorHandler {
            return errorHandler
        }
    },


    INIT_DEFAULT {
        private val errorHandler: IErrorHandler = InitDefaultErrorHandler()

        override fun getErrorHandler(): IErrorHandler {
            return errorHandler
        }
    };

    abstract fun getErrorHandler(): IErrorHandler?
}