# tuic

Work in progress...

## Development

### Working with migrations

#### Up migrations

```bash
$ bin/repl
```

```
user=> (use 'tuic.migrations)
user=> (create! (timestamp))
```

Write some up and down sql code to created migration files...

```
user=> (migrate!)
```

#### Down migrations

```
user=> (down!)
```

### Run tests

```bash
$ bin/test
```

## Installation

FIXME

## Usage

FIXME: explanation

    $ java -jar tuic-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Roadmap

* Plug TravisCI
* Write more tests
* Users sign up and sign in via "friend"
...

## License

Copyright © 2017 @squad

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
