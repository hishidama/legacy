package jp.hishidama.hadoop.conf;

import java.util.*;

import org.apache.hadoop.conf.Configuration;

// FlowConnector.setApplicationJarClass()Ç≈ClassÇì¸ÇÍÇÈÇ©ÇÁÅA
// StringÇÃÇ›Ç≈Ç†ÇÈConfigurationÇ≈ÇÕÉ_ÉÅ
@Deprecated
public class ConfigurationMap extends AbstractMap<Object, Object> {

	protected Configuration conf;

	public ConfigurationMap(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public int size() {
		return conf.size();
	}

	@Override
	public boolean containsKey(Object key) {
		String name = (String) key;
		if (conf.get(name) != null) {
			return true;
		}
		final String DEFAULT = "default";
		return !DEFAULT.equals(conf.get(name, DEFAULT));
	}

	@Override
	public Object get(Object key) {
		String name = (String) key;
		return conf.get(name);
	}

	@Override
	public Object put(Object key, Object value) {
		String name = (String) key;
		Object oldValue = conf.get(name);
		conf.set(name, (String) value);
		return oldValue;
	}

	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		conf.clear();
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return new AbstractSet<Entry<Object, Object>>() {

			@Override
			public Iterator<Entry<Object, Object>> iterator() {
				return new Iterator<Entry<Object, Object>>() {
					Iterator<Entry<String, String>> i = conf.iterator();

					@Override
					public boolean hasNext() {
						return i.hasNext();
					}

					@Override
					public Entry<Object, Object> next() {
						final Entry<String, String> e = i.next();
						return new Entry<Object, Object>() {

							@Override
							public Object getKey() {
								return e.getKey();
							}

							@Override
							public Object getValue() {
								return e.getValue();
							}

							@Override
							public Object setValue(Object value) {
								return e.setValue((String) value);
							}
						};
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			@Override
			public int size() {
				return conf.size();
			}
		};
	}
}
