package signup



import org.junit.*
import grails.test.mixin.*

@TestFor(JoinItemController)
@Mock(JoinItem)
class JoinItemControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/joinItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.joinItemInstanceList.size() == 0
        assert model.joinItemInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.joinItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.joinItemInstance != null
        assert view == '/joinItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/joinItem/show/1'
        assert controller.flash.message != null
        assert JoinItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/joinItem/list'


        populateValidParams(params)
        def joinItem = new JoinItem(params)

        assert joinItem.save() != null

        params.id = joinItem.id

        def model = controller.show()

        assert model.joinItemInstance == joinItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/joinItem/list'


        populateValidParams(params)
        def joinItem = new JoinItem(params)

        assert joinItem.save() != null

        params.id = joinItem.id

        def model = controller.edit()

        assert model.joinItemInstance == joinItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/joinItem/list'

        response.reset()


        populateValidParams(params)
        def joinItem = new JoinItem(params)

        assert joinItem.save() != null

        // test invalid parameters in update
        params.id = joinItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/joinItem/edit"
        assert model.joinItemInstance != null

        joinItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/joinItem/show/$joinItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        joinItem.clearErrors()

        populateValidParams(params)
        params.id = joinItem.id
        params.version = -1
        controller.update()

        assert view == "/joinItem/edit"
        assert model.joinItemInstance != null
        assert model.joinItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/joinItem/list'

        response.reset()

        populateValidParams(params)
        def joinItem = new JoinItem(params)

        assert joinItem.save() != null
        assert JoinItem.count() == 1

        params.id = joinItem.id

        controller.delete()

        assert JoinItem.count() == 0
        assert JoinItem.get(joinItem.id) == null
        assert response.redirectedUrl == '/joinItem/list'
    }
}
