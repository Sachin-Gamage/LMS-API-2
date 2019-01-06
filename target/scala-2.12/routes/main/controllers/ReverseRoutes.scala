// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Saching/Projects/library-management/LibraryManagemenApi/conf/routes
// @DATE:Sun Dec 09 21:45:46 IST 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def getBookByISBN(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "getBook")
    }
  
    // @LINE:17
    def addDvd(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "addDvd")
    }
  
    // @LINE:14
    def books(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "books")
    }
  
    // @LINE:19
    def getDvdByISBN(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "getDvd")
    }
  
    // @LINE:23
    def burrowBook(): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "burrowBook")
    }
  
    // @LINE:16
    def addBook(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "addBook")
    }
  
    // @LINE:26
    def returnDvd(): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "returnDvd")
    }
  
    // @LINE:25
    def burrowDvd(): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "burrowDvd")
    }
  
    // @LINE:15
    def dvds(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "dvds")
    }
  
    // @LINE:24
    def returnBook(): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "returnBook")
    }
  
    // @LINE:20
    def getAllItems(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "getAllItems")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:21
    def deleteBook(): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "deleteBook")
    }
  
    // @LINE:22
    def deleteDvd(): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "deleteDvd")
    }
  
  }

  // @LINE:9
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
