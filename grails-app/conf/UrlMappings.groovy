import signup.ActivityGroup

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index", controller: "ActivityGroup", action: "index")
		"500"(view:'/error')
	}
}
