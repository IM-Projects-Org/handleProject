FROM ubuntu:latest
LABEL authors="EC21"

ENTRYPOINT ["top", "-b"]