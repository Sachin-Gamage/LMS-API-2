// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Saching/Projects/library-management/LibraryManagemenApi/conf/routes
// @DATE:Sun Dec 09 21:45:46 IST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
