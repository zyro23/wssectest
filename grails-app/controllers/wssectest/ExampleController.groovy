package wssectest

import grails.plugin.springsecurity.annotation.Secured
import grails.web.controllers.ControllerMethod

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.access.prepost.PreAuthorize

class ExampleController {

	@Secured("permitAll")
	def index() {}
	
	@ControllerMethod
	@MessageMapping("/hello")
	@PreAuthorize("hasRole('USER')")
	@SendTo("/topic/hello")
	String hello(String world) {
		return "hello from controller, ${world}!"
	}
	
}
