package pa.iscde.checkstyle.internal.config.sizes;

import java.util.ArrayList;
import java.util.List;

import pa.iscde.checkstyle.internal.config.Config;
import pa.iscde.checkstyle.internal.config.ConfigProperty;

public class LineLengthConfig implements Config {
	private static final int NUMBER_OF_PROPERTIES = 2;

	private static final String NAME = "LineLength";

	private static List<ConfigProperty> PROPERTIES;

	static {
		loadProperties();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public List<ConfigProperty> getProperties() {
		return PROPERTIES;
	}

	private static void loadProperties() {
		if (PROPERTIES == null) {
			PROPERTIES = new ArrayList<ConfigProperty>(NUMBER_OF_PROPERTIES);
		}

		final ConfigProperty max = new ConfigProperty("max", "80", true);
		final ConfigProperty ignorePattern = new ConfigProperty("ignorePattern", "^ *\\* *[^ ]+$", true);

		PROPERTIES.add(max);
		PROPERTIES.add(ignorePattern);
	}
}
