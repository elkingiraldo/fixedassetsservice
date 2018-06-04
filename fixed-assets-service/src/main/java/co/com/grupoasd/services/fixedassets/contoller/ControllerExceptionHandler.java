package co.com.grupoasd.services.fixedassets.contoller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.grupoasd.services.fixedassets.constants.FixedAssetsServiceConstants;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.exception.ServiceExceptionWrapper;

/**
 * Exception management controller
 * 
 * @author egiraldo
 *
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@Value("${spring.application.name}")
	private String applicationName;

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ServiceExceptionWrapper> handleUnknownException(Exception ex, WebRequest request) {
		logger.error("User management service -> exception occurred " + ex.getMessage(), ex);
		ex.printStackTrace();

		String code = FixedAssetsServiceErrorCodes.GENERAL_EXCEPTION.getMessage();

		Locale locale = getLocale(request);

		String errorMessage = messageSource.getMessage(code, null, locale);

		ServiceExceptionWrapper exception = new ServiceExceptionWrapper(code, errorMessage, null);

		return new ResponseEntity<ServiceExceptionWrapper>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { FixedAssetsServiceException.class })
	protected ResponseEntity<ServiceExceptionWrapper> handleReportServiceException(FixedAssetsServiceException ex,
			WebRequest request) {
		logger.error("Fixed Assets service -> managed exception occurred " + ex.getMessage(), ex);
		ex.printStackTrace();

		Locale locale = getLocale(request);

		String errorCode = ex.getCode().getMessage();

		String errorMessage = messageSource.getMessage(ex.getCode().getMessage(), null, locale);

		String errorDetail = null;

		if (ex.getCode().getErrorDetail() != null) {
			errorDetail = messageSource.getMessage(ex.getCode().getErrorDetail(), null, locale);
		} else {
			errorDetail = ex.getMessage();
		}

		ServiceExceptionWrapper exception = new ServiceExceptionWrapper(errorCode, errorMessage, errorDetail,
				ex.getCode().getHttpStatus().value());

		exception.setErrorOrigin(applicationName);

		return new ResponseEntity<ServiceExceptionWrapper>(exception, ex.getCode().getHttpStatus());
	}

	private Locale getLocale(WebRequest request) {
		String localeString = request.getHeader(FixedAssetsServiceConstants.LOCALE_HEADER_NAME);

		Locale locale = null;
		if (localeString != null) {
			locale = new Locale(localeString);
		}

		if (locale == null) {
			locale = LocaleContextHolder.getLocale();
		}
		return locale;
	}

}
