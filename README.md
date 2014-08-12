## What

[PuppetDB](https://docs.puppetlabs.com/puppetdb/) stores an awful lot of
data about your infrastructure. Better than just storing it though it
provides a powerful API for querying that information. This repository
shows a very simple example of writing tests against PuppetDB data.

## Why

Why might you want to do such a think I hear you asking:

* Monitoring - Checks for nodes that haven't reported in?
* Test driven infrastructure - Maybe you want to assert that you have at
  least 10 haproxy nodes, or no instances older than a week

The advantage of querying the information in PuppetDB is that it's
incredibly fast. The obvious disadvantage is that it's second hand data,
although see
[serverspec-puppetdb](https://github.com/garethr/serverspec-puppetdb)
that could help with nerves there.

## How

The tests are just clojure, although you could implement the same idea
in any language with a PuppetDB client. The syntax uses
[expectations](http://jayfields.com/expectations/) which leads to some
pretty expressive code:

```clojure
(expect 2 (count nodes))
```

You can run the tests with the following. Note that the specific tests
here are unlikely to pass with anything but the small test dataset I
was using.

    lein expectations

Much more fun is running the tests everytime you make a change to the
test file.

    lein autoexpect

## Thanks

Thanks to [clj-puppetdb](https://github.com/holguinj/clj-puppetdb) for
dealing with the PuppetDB interface so I could get on with the job.
