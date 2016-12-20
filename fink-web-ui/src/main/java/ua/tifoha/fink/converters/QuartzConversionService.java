package ua.tifoha.fink.converters;

import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.utils.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import ua.tifoha.fink.services.JobService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class QuartzConversionService extends GenericConversionService implements GenericConverter {
	protected static Logger logger = LoggerFactory.getLogger(QuartzConversionService.class);
	private JobService jobService;
	Set<ConvertiblePair> convetableTypes = new LinkedHashSet<>();

    public QuartzConversionService() {
        super();
        addConverter(String.class, JobKey.class, new StringToKeyConverter<>(JobKey::new));
        addConverter(String.class, TriggerKey.class, new StringToKeyConverter<>(TriggerKey::new));
        addConverter(Key.class, String.class, new KeyToStringConverter());
    }

	@Override
	public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<? super S, ? extends T> converter) {
		convetableTypes.add(new ConvertiblePair(sourceType, targetType));
		super.addConverter(sourceType, targetType, converter);
	}

	public static class StringToKeyConverter<T extends Key<T>> implements Converter<String, T> {
		private final BiFunction<String, String, T> constructor;

        public StringToKeyConverter(BiFunction<String, String, T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T convert(String source) {
			try {
				String[] strings = source.split("\\.", 2);
				return constructor.apply(strings[1], strings[0]);
			} catch (Exception e) {
				logger.warn("Fail to konvert'{}' into key", source);
				return null;
			}
		}
    }

    public static class KeyToStringConverter implements Converter<Key, String> {

        @Override
        public String convert(Key source) {
            return source.getGroup() + ":" + source.getName();
        }
    }

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return convetableTypes;
	}
}

