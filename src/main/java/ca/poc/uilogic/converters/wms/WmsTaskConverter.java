package ca.poc.uilogic.converters.wms;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import ca.poc.uilogic.domain.Task;
import ca.poc.uilogic.domain.wms.WmsTask;

/**
 * Converter class for WSMTask class. It converts WMSTask to JSON task
 * representation understandable to Angular-based application front-end.
 * 
 * @author daniel.fryze
 */
public class WmsTaskConverter {

	/**
	 * A conversion method.
	 */
	public static Task convert(WmsTask wmsTask) {

		if (wmsTask == null) {
			return null;
		}

		Task result = new Task();

		result.setId(wmsTask.getTaskID());
		result.setClient(wmsTask.getTaskInfo().getCreatedBy());
		result.setStatus(wmsTask.getTaskInfo().getStatus());
		result.setTitle(wmsTask.getTaskInfo().getName());

		try {

			if (wmsTask.getTaskInfo().getExpireDate() != null) {
				LocalDate localDate1 = LocalDate.parse(wmsTask.getTaskInfo().getExpireDate(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy"));
				Date expirationDateDate1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
				result.setExpirationDate(expirationDateDate1.getTime());
			}

			if (wmsTask.getTaskInfo().getCreatedDate() != null) {
				LocalDate localDate2 = LocalDate.parse(wmsTask.getTaskInfo().getCreatedDate(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy"));
				Date expirationDateDate2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
				result.setSetDate(expirationDateDate2.getTime());
			}
		} catch (DateTimeParseException exc) {
			// Temporarily for POC purposes we leave it empty (not good)
		}

		return result;
	}
}