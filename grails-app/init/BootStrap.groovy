import grails.core.GrailsApplication

import javax.servlet.Filter

import org.springframework.boot.context.embedded.FilterRegistrationBean

import wssectest.Role
import wssectest.User
import wssectest.UserRole

class BootStrap {
	
	GrailsApplication grailsApplication

    def init = { servletContext ->
		def role = new Role(authority: "ROLE_USER").save()
		def user = new User(username: "user", password: "user").save()
		new UserRole(user, role).save()
    }
    def destroy = {
    }
}
