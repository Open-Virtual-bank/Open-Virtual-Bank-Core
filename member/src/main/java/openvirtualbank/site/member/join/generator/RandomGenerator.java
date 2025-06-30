package openvirtualbank.site.member.join.generator;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerator {
	private static final Random random = new Random();

	public int makeRandomNumber() {

		return random.nextInt(888888) + 111111;
	}
}
