package linq;

import java.util.HashMap;
import java.util.stream.Collectors;

public class ObjectDumper {
	public static void dump(HashMap<String, Object> map) {
		String result = map.entrySet().stream()
				.map(x -> String.format(":%s %s", x.getKey(), x.getValue()))
				.collect(Collectors.joining(" "));
		System.out.println(result);
	}
}
