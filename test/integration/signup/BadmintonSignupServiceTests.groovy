package signup



import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BadmintonSignupService)
class BadmintonSignupServiceTests {
    BadmintonSignupService badmintonSignupService

    void testListJoins() {
        def result = badmintonSignupService.listUsers([],"1", true)
    }
}
