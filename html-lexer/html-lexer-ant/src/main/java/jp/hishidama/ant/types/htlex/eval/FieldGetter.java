package jp.hishidama.ant.types.htlex.eval;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.net.URI;

/**
 * HtHtmlLexer属性取得ユーティリティー.
 *<p>
 * 値の属性の取得を行うユーティリティー。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.05
 */
public class FieldGetter {

	protected static FieldGetter INSTANCE = null;

	public static FieldGetter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FieldGetter();
		}
		return INSTANCE;
	}

	/**
	 * 属性値取得.
	 *
	 * @param key
	 *            属性取得名
	 * @return 値（属性取得名が未定義の場合はnull）
	 */
	public Getter get(String key) {
		initGetterMap();

		Object obj = GETTER_MAP.get(key);
		if (obj == null) {
			return null;
		}

		Getter getter;
		if (obj instanceof Class) {
			@SuppressWarnings("unchecked")
			Class<Getter> c = (Class<Getter>) obj;
			try {
				getter = c.newInstance();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new InternalError(e.toString());
			}
			GETTER_MAP.put(key, getter);
		} else {
			getter = (Getter) obj;
		}

		return getter;
	}

	protected Map<String, Object> GETTER_MAP = null;

	protected void initGetterMap() {
		if (GETTER_MAP != null) {
			return;
		}
		GETTER_MAP = new HashMap<String, Object>();
		GETTER_MAP.put("name", GetName.class);
		GETTER_MAP.put("value", GetValue.class);
		GETTER_MAP.put("uri_scheme", GetUriScheme.class);
		GETTER_MAP.put("uri_host", GetUriHost.class);
		GETTER_MAP.put("uri_port", GetUriPort.class);
		GETTER_MAP.put("uri_path", GetUriPath.class);
		GETTER_MAP.put("uri_context", GetUriContext.class);
		GETTER_MAP.put("uri_query", GetUriQuery.class);
		GETTER_MAP.put("uri_query_map", GetUriQueryMap.class);
		GETTER_MAP.put("uri_fragment", GetUriFragment.class);
		GETTER_MAP.put("uri_schemePath", GetUriSchemePath.class);
		GETTER_MAP.put("uri_schemeQuery", GetUriSchemeQuery.class);
	}

	protected static abstract class Getter {
		abstract Object get(Object obj);

		protected String getStringValue(Object obj) {
			if (obj instanceof String) {
				return (String) obj;
			}
			if (obj instanceof AttributeToken) {
				return ((AttributeToken) obj).getValue();
			}
			return obj.toString();
		}

		protected URI getURI(Object obj) {
			if (obj instanceof URI) {
				return (URI) obj;
			}
			if (obj instanceof File) {
				File f = (File) obj;
				return URI.valueOf(f);
			}
			if (obj instanceof java.net.URI) {
				java.net.URI urj = (java.net.URI) obj;
				return URI.valueOf(urj);
			}
			return new URI(getStringValue(obj));
		}
	}

	protected static class GetName extends Getter {
		@Override
		public Object get(Object obj) {
			if (obj instanceof AttributeToken) {
				return ((AttributeToken) obj).getName();
			}
			if (obj instanceof Tag) {
				return ((Tag) obj).getName();
			}
			return null;
		}
	}

	protected static class GetValue extends Getter {
		@Override
		public Object get(Object obj) {
			if (obj instanceof AttributeToken) {
				return ((AttributeToken) obj).getValue();
			}
			return obj;
		}
	}

	protected static class GetUriScheme extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getScheme();
			}
			return null;
		}
	}

	protected static class GetUriHost extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getHost();
			}
			return null;
		}
	}

	protected static class GetUriPort extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getPort();
			}
			return null;
		}
	}

	protected static class GetUriPath extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getPath();
			}
			return null;
		}
	}

	protected static class GetUriContext extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				String path = uri.getPath();
				if (path != null && path.startsWith("/")) {
					int n = path.indexOf('/', 1);
					if (n < 0) {
						return path.substring(1);
					} else {
						return path.substring(1, n);
					}
				}
			}
			return null;
		}
	}

	protected static class GetUriQuery extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getQuery();
			}
			return null;
		}
	}

	/**
	 * クエリー文字列をマップに変換する。
	 * <p>
	 * 例えば「name1=value1&name2=value2」は、キーをname1・name2とするマップにする。<br>
	 * 当メソッドでは、重複する名前があった場合は、後の値のみが保持される。
	 * </p>
	 */
	protected static class GetUriQueryMap extends GetUriQuery {
		@Override
		public Object get(Object obj) {
			String query = (String) super.get(obj);
			if (query != null) {
				// TODO+++本当はもっと複雑だろう
				String[] ss = query.split("\\&");
				Map<String, String> map = new HashMap<String, String>(ss.length);
				for (String s : ss) {
					int n = s.indexOf('=');
					if (n < 0) {
						map.put(s, null);
					} else {
						map.put(s.substring(0, n), s.substring(n + 1));
					}
				}
				return map;
			}
			return null;
		}
	}

	protected static class GetUriFragment extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getFragment();
			}
			return null;
		}
	}

	protected static class GetUriSchemePath extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getSchemePath();
			}
			return null;
		}
	}

	protected static class GetUriSchemeQuery extends Getter {
		@Override
		public Object get(Object obj) {
			URI uri = getURI(obj);
			if (uri != null) {
				return uri.getSchemeQuery();
			}
			return null;
		}
	}
}
