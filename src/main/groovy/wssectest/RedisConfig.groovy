package wssectest;

import org.springframework.beans.BeansException
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import redis.clients.jedis.Protocol
import redis.embedded.RedisServer

/**
 * Redis is required for {@link EnableRedisHttpSession} handling.
 */
@Configuration
class RedisConfig {

	@Bean
	static RedisServerBean redisServer() {
		return new RedisServerBean()
	}
	
	/**
	 * Implements BeanDefinitionRegistryPostProcessor to ensure this Bean is
	 * initialized before any other Beans. Specifically, we want to ensure that
	 * the Redis Server is started before RedisHttpSessionConfiguration attempts
	 * to enable Keyspace notifications.
	 */
	static class RedisServerBean implements InitializingBean, DisposableBean, BeanDefinitionRegistryPostProcessor {

		private RedisServer redisServer

		@Override
		void afterPropertiesSet() throws Exception {
			redisServer = RedisServer.builder().setting("maxheap 128mb").setting("bind 127.0.0.1").port(Protocol.DEFAULT_PORT).build()
			redisServer.start()
		}

		@Override
		void destroy() throws Exception {
			redisServer?.stop()
		}

		@Override
		void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {}

		@Override
		void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

	}
}