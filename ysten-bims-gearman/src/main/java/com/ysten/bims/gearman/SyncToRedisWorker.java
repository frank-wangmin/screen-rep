package com.ysten.bims.gearman;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * The echo worker polls jobs from a job server and execute the echo function.
 * 
 * The echo worker illustrates how to setup a basic worker
 */
public class SyncToRedisWorker implements GearmanFunction {

	/** The echo function name */
	public static final String FUNCTION_NAME = "syncToRedis";

	/** The host address of the job server */
	private static final String HOST = "192.168.3.41";

	/** The port number the job server is listening on */
	private static final int PORT = 4730;

	private String gearmanServer = HOST;
	private int gearmanPort = PORT;

	List<RedisTemplate<String, String>> redisTemplateList = new ArrayList<RedisTemplate<String, String>>();

	private static final Logger logger = LoggerFactory
			.getLogger(SyncToRedisWorker.class);

	private StringRedisSerializer serializer;

	public SyncToRedisWorker() {
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "classpath:spring/applicationContext.xml");
		// redisTemplate = (RedisTemplate<String, String>) context
		// .getBean("redisTemplate");
	}

	private void createRedisTemplate(String redisHost, String redisPort) {
		org.springframework.data.redis.connection.jedis.JedisConnectionFactory factory = new org.springframework.data.redis.connection.jedis.JedisConnectionFactory();
		redis.clients.jedis.JedisPoolConfig config = new redis.clients.jedis.JedisPoolConfig();
		config.setMaxActive(10000);
		config.setMaxIdle(200);
		config.setMaxWait(12000);
		config.setTestOnBorrow(true);
		factory.setPoolConfig(config);
		factory.setHostName(redisHost);
		factory.setPort(Integer.parseInt(redisPort));
		factory.afterPropertiesSet();
		org.springframework.data.redis.core.RedisTemplate<String, String> redisTemplate = new org.springframework.data.redis.core.RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(factory);
		serializer = new org.springframework.data.redis.serializer.StringRedisSerializer();
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setDefaultSerializer(serializer);
		redisTemplate.afterPropertiesSet();
		redisTemplateList.add(redisTemplate);
	}

	private void parseParam(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("h", true, "Gearman host");
		options.addOption("p", true, "Gearman port");
		options.addOption("redisList", true,
				"Redis List, host:port list, separate by ,");
		try {
			// Parse the program arguments
			CommandLine commandLine = parser.parse(options, args);
			// Set the appropriate variables based on supplied options

			if (commandLine.hasOption('h')) {
				gearmanServer = commandLine.getOptionValue("h");
			} else {
				System.out
						.println("请指定Gearman服务器地址，Usage: %JDK1.7%/bin/java -jar -Xms256m -Xmx1024m ysten-bims-gearman.jar -h host -p port -redisList redisHost1:redisPort1, redisHost2:redisPort2");
				 System.exit(-1);
			}
			if (commandLine.hasOption('p')) {
				try {
					gearmanPort = Integer.parseInt(commandLine
							.getOptionValue("p"));
				} catch (NumberFormatException e) {
					logger.error("port must be integer");
					System.exit(-1);
				}
			}
			if (commandLine.hasOption("redisList")) {
				String[] redisList = commandLine.getOptionValue("redisList")
						.split(",");
				if (redisList.length > 0) {
					for (String str : redisList) {
						String[] redisConfig = str.split(":");
						createRedisTemplate(redisConfig[0], redisConfig[1]);
					}
				} else {
					System.out
							.println("请指定Redis服务器地址，可指定多个，以逗号分隔，Usage: %JDK1.7%/bin/java -jar -Xms256m -Xmx1024m ysten-bims-gearman.jar -h host -p port -redisList redisHost1:redisPort1, redisHost2:redisPort2");
					System.exit(-1);
				}
			} else {
				System.out
						.println("请指定Redis服务器地址，可指定多个，以逗号分隔，Usage: %JDK1.7%/bin/java -jar -Xms256m -Xmx1024m ysten-bims-gearman.jar -h host -p port -redisList redisHost1:redisPort1, redisHost2:redisPort2");
				System.exit(-1);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			System.out.println(e.toString());
			System.exit(-1);
		}
	}

	public static void main(String... args) {
		SyncToRedisWorker worker = new SyncToRedisWorker();
		worker.parseParam(args);
		worker.registerServer();
	}

	private void registerServer() {
		/*
		 * Create a Gearman instance
		 */
		Gearman gearman = Gearman.createGearman();

		/*
		 * Create the job server object. This call creates an object represents
		 * a remote job server.
		 * 
		 * Parameter 1: the host address of the job server. Parameter 2: the
		 * port number the job server is listening on.
		 * 
		 * A job server receives jobs from clients and distributes them to
		 * registered workers.
		 */
		logger.debug(" connect to gearman server: " + gearmanServer + ":"
				+ gearmanPort);
		GearmanServer server = gearman.createGearmanServer(gearmanServer,
				gearmanPort);

		/*
		 * Create a gearman worker. The worker poll jobs from the server and
		 * executes the corresponding GearmanFunction
		 */
		GearmanWorker worker = gearman.createGearmanWorker();

		/*
		 * Tell the worker how to perform the echo function
		 */
		worker.addFunction(FUNCTION_NAME, this);
		/*
		 * Tell the worker that it may communicate with the this job server
		 */
		worker.addServer(server);
	}

	@Override
	public byte[] work(String function, byte[] data,
			GearmanFunctionCallback callback) throws Exception {
		/*
		 * The work method performs the gearman function. In this case, the echo
		 * function simply returns the data it received
		 */
		String decoded = new String(data, "UTF-8");
		ISyncParam param = SyncParam.parseSyncParam(decoded);
		if (param != null) {
			SyncType syncType = param.getSyncType();
			logger.debug(" receive work: " + param);

			List<String> deleteList = new ArrayList<String>();
			for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
				Set<String> set = redisTemplate.keys(param.getQueryString());
				deleteList.clear();
				for (String str : set) {
					if (syncType == SyncType.PANEL_STYLE_DATA) {
						if (!(str.endsWith(",type:data") || str
								.endsWith(",type:style"))) {
							continue;
						}
					}
					deleteList.add(str);
				}
				delete(redisTemplate, deleteList);
			}
		}
		return null;
	}

	public void delete(final RedisTemplate<String, String> redisTemplate,
			final List<String> keyList) {
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				try {
					for (String key : keyList) {
						byte[] value = connection.get(key.getBytes());
						if (value != null) {
							String interfaceKey = new String(value);
							logger.debug("remove redis key: " + interfaceKey);
							redisTemplate.delete(key);
							redisTemplate.delete(interfaceKey);
						}
					}
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});
	}
}
