#!/bin/zsh

function b {
	./gradlew build
}

function r {
	local args=""
	if [[ $# -gt 0 ]]; then
		./gradlew run --args="$*[*]"
	else
		./gradlew run
	fi
}
