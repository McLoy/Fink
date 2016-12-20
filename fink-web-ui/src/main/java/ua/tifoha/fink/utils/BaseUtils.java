package ua.tifoha.fink.utils;

import java.lang.reflect.Array;

public interface BaseUtils {
	//	static ModelAndView getEditModel(BaseModel model) {
//		ModelAndView view = new ModelAndView(model.getName() + "/edit");
//		view.addObject("timeUnits", TimeUnit.values());
//		view.addObject("entity", model.getTitle());
//		view.addObject("title", "Trigger");
//		view.addObject("calendarNames", jobService.getCalendarNames());
//		return view;
//	}
	@SuppressWarnings ("unchecked")
	static <T> T getDefaultValue(Class<T> clazz) {
		return (T) Array.get(Array.newInstance(clazz, 1), 0);
	}
}
