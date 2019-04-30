# For some reason the on in meta-openembedded doesn't have nativesdk.
# We need it so we can build tuf nativesdk, though.
BBCLASSEXTEND += "nativesdk"
