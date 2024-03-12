# Changelog

## [4.0.0](https://github.com/joke/spock-outputcapture/compare/v3.0.1...v4.0.0) (2024-03-12)


### ⚠ BREAKING CHANGES

* lines should be empty in case of no output. closes #157
* drop support for spock 1.3

### Features

* allow manually clearing the captured output ([ad73542](https://github.com/joke/spock-outputcapture/commit/ad7354283e989734adbd550870095c273a25a6e1))
* drop support for spock 1.3 ([6b7695a](https://github.com/joke/spock-outputcapture/commit/6b7695ac7c1bd61231ab7948ccc3c41c2db690a8))
* support framework 2.4 ([d08a363](https://github.com/joke/spock-outputcapture/commit/d08a363fcac59035cbc7a13c4dfcb22b7521f6ff))


### Bug Fixes

* lines should be empty in case of no output. closes [#157](https://github.com/joke/spock-outputcapture/issues/157) ([3ea0a84](https://github.com/joke/spock-outputcapture/commit/3ea0a84ac9a22e4bfed6ad454301b86438868d70))

## [3.0.1](https://github.com/joke/spock-outputcapture/compare/v3.0.0...v3.0.1) (2022-09-05)


### Bug Fixes

* base class issue with older groovy versions ([9c461af](https://github.com/joke/spock-outputcapture/commit/9c461af3d5fc8e655b40e358feeae5c488b3b0d0))

## 3.0.0 (2022-03-18)


### ⚠ BREAKING CHANGES

* no longer include transitive dependency on spock 2.0. Which was wrong anyway.

### Features

* split lines also with Windows, Mac and mixed line endings ([1688999](https://github.com/joke/spock-outputcapture/commit/16889998d25ff2ee44734635026cc0b543cfcffe))


### Bug Fixes

* spock 1.3 support ([2bd8524](https://github.com/joke/spock-outputcapture/commit/2bd8524bb829572a947318831c28b92787a598d8))


### Miscellaneous Chores

* release 3.0.0 ([c5cc2bd](https://github.com/joke/spock-outputcapture/commit/c5cc2bd62eba5be431cd309c4502fbdcfddcf649))
