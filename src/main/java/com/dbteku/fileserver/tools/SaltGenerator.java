package com.dbteku.fileserver.tools;

import java.util.Random;

public class SaltGenerator {

	private static final int NUMBER_LOW = 48;
	private static final int NUMBER_HIGH = 58;
	private static final int UPPER_CASE_LOW = 65;
	private static final int UPPER_CASE_HIGH = 91;
	private static final int LOWER_CASE_LOW = 97;
	private static final int LOWER_CASE_HIGH = 123;
	private static final int DEFAULT_RANGE_LOW = 33;
	private static final int DEFAULT_RANGE_HIGH = 127;
	private static final int DEFAULT_SIZE = 64;
	private static CharGenerator[] GENERATORS = {
	};
	
	private int size;
	
	public SaltGenerator(int size) {
		this.size = size;
		if(GENERATORS != null){
			GENERATORS = new CharGenerator[]{
					new CharGenerator(NUMBER_HIGH, NUMBER_LOW),
					new CharGenerator(LOWER_CASE_HIGH, LOWER_CASE_LOW),
					new CharGenerator(UPPER_CASE_HIGH, UPPER_CASE_LOW)
			};
		}
	}
	
	public SaltGenerator() {
		this(DEFAULT_SIZE);
	}
	
	public String generateNumbersAndLettersOnly(){
		StringBuilder builder = new StringBuilder();
		
		Random charRandom = new Random();
		Random decisionRandom = new Random();
		for (int i = 0; i < size; i++) {
			CharGenerator generator = GENERATORS[decisionRandom.nextInt(GENERATORS.length)];
			builder.append(generator.generate(charRandom));
		}
		
		return builder.toString();
	}
	
	public String generate(){
		StringBuilder builder = new StringBuilder();
		
		Random charRandom = new Random();
		CharGenerator generator = new CharGenerator(DEFAULT_RANGE_HIGH, DEFAULT_RANGE_LOW);
		for (int i = 0; i < size; i++) {
			builder.append(generator.generate(charRandom));
		}
		
		return builder.toString();
	}
	
	private class CharGenerator{
		private int high;
		private int low;
		
		public CharGenerator(int high, int low) {
			this.high = high;
			this.low = low;
		}
		
		public char generate(Random random){
			return (char) (random.nextInt(high-low) + low);
		}
		
	}
	
}

