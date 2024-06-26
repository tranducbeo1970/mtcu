# tune glibc memory allocation, optimize for low fragmentation
# limit the number of arenas
export MALLOC_ARENA_MAX=2
# disable dynamic mmap threshold, see M_MMAP_THRESHOLD in "man mallopt"
export MALLOC_MMAP_THRESHOLD_=131072
export MALLOC_TRIM_THRESHOLD_=131072
export MALLOC_TOP_PAD_=131072
export MALLOC_MMAP_MAX_=65536
java -Djava.library.path=/opt/isode/lib:/opt/isode/lib/java/classes -Dlog4j.configurationFile="config/log4j2.xml" -jar com.attech.amhs.mtcu.jar 


