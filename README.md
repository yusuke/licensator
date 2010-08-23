Licensator - The Open Source License Adviser
============================================

Licensator is a little web application written just for fun in about 48 hours.


About Licensator
----------------

If you do some research, you'll find that there are [literally hundreds][oss-licenses]  of open source licenses. Most of them are not used anymore, or are superseded by other licenses, or are incompatible with other licenses.

As you can see, things can get ugly. That's why we wrote this app.


Prerequisites
-------------

* [Clojure][clj] 1.2.0;
* [Clojure Contrib][contrib] 1.2.0;
* [Leiningen][lein] 1.3.0.


Running Licensator
------------------

This is a three-step process:

1. Run `lein deps` to get the app's dependencies;
2. Run `lein run server` to start the app in a local [Jetty][jetty] instance;
3. Point your browser to [http://localhost:8080][dev] and have fun!


[clj]: http://clojure.org/
[compojure]: http://github.com/weavejester/compojure
[contrib]: http://github.com/clojure/clojure-contrib
[dev]: http://localhost:8080
[jetty]: http://jetty.codehaus.org/jetty/
[lein]: http://github.com/technomancy/leiningen
[oss-licenses]: http://opensource.org/licenses/
