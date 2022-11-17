#!/bin/zsh

function b {
	./gradlew build
}

function r {
	./gradlew run --args="$*[*]"
}
