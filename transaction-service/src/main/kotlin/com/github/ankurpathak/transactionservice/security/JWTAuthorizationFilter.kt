package com.github.ankurpathak.transactionservice.security
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JWTAuthorizationFilter(private val secParams: SecParams) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        var jwt = request.getHeader(AUTHORIZATION)
        if (jwt == null || !jwt.startsWith(BEARER)) {
            filterChain.doFilter(request, response)
            return
        }
        val verifier = JWT.require(Algorithm.HMAC256(secParams.secret)).build()
        jwt = jwt.substring(7)
        val decodedJWT = verifier.verify(jwt)

        val username = decodedJWT.subject
        val roles = decodedJWT.claims[ROLES]!!.asList(String::class.java)
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()

        for (r in roles) {
            authorities.add(SimpleGrantedAuthority(r))
        }

        val user = UsernamePasswordAuthenticationToken(username, null, authorities)
        SecurityContextHolder.getContext().authentication = user
        filterChain.doFilter(request, response)
    }

    companion object {
        private const val BEARER = "Bearer "
        private const val ROLES = "roles"
        private const val AUTHORIZATION = "Authorization"
    }
}
