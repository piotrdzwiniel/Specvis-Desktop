import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import scipy.ndimage as sn

# =================================== User-defined settings ===================================

file_paths = {
    'session_info': 'session_info.csv',  # File path of session info file.
    'session_data': 'session_data.csv'  # File path of session data file.
}

args = {
    'threshold': 'decibel',  # Type of threshold: brightness, luminance, decibel
    'fig_type': 'contour',  # Type of figure: imshow, contour
    'fig_size': (8, 6), # Size of the figure (width, height)
    'cmap': 'binary_r',  # Colormap for visualization, see: https://matplotlib.org/stable/users/explain/colors/colormaps.html. For decibel threshold, use reversed colormap, e.g. jet_r
    'contour_interp': 20,  # Interpolation factor for contour map. If zero, no interpolation is applied.
    'wh_type': 'degrees',  # Units for x and y axes: pixels, degrees
    'vmax_vmin': 'session_info',  # Source for vmax and vmin: session_data, session_info

    # Additional arguments specific to 'contour' fig_type.
    'contour_levels': 10,  # Number of levels for contour map
    'show_contour_lines': True,  # Show isolines on contour map
    'clabel_color': 'black',  # Color of isolines labels
    'clabel_font_size': 12  # Font size of isolines labels
}

# =============================================================================================

# Read session info CSV file and store it in args['df_si']
args['df_si'] = pd.read_csv(file_paths['session_info'])

# Read session data CSV file and store it in args['df_sd']
args['df_sd'] = pd.read_csv(file_paths['session_data'])

# Extract ranges for stimulus brightness, luminance, and decibel
args['stim_bright_range'] = [
    args['df_si'].loc[args['df_si']['Parameter'] == 'Stimulus min brightness (%)', 'Value'].iloc[0],
    args['df_si'].loc[args['df_si']['Parameter'] == 'Stimulus max brightness (%)', 'Value'].iloc[0]
]

args['stim_lum_range'] = [
    args['df_si'].loc[args['df_si']['Parameter'] == 'Stimulus min luminance (cd/m2)', 'Value'].iloc[0],
    args['df_si'].loc[args['df_si']['Parameter'] == 'Stimulus max luminance (cd/m2)', 'Value'].iloc[0]
]

args['stim_db_range'] = [
    args['df_si'].loc[args['df_si']['Parameter'] == 'Decibel range (dB)', 'Value'].iloc[0].split('/')[0],
    args['df_si'].loc[args['df_si']['Parameter'] == 'Decibel range (dB)', 'Value'].iloc[0].split('/')[1]
]

# Get unique values for PosPxX and PosPxY
x_values, y_values = [], []
x_label, y_label = None, None
x_param, y_param = None, None

# Sort session data by PosPxY and PosPxX
args['df_sd'] = args['df_sd'].sort_values(by=['PosPxY', 'PosPxX'])

# Determine the units for x and y axes
if args['wh_type'] == 'pixels':
    x_values, y_values = args['df_sd']['PosPxX'].unique(), args['df_sd']['PosPxY'].unique()
    x_label, y_label = 'Position X (px)', 'Position Y (px)'
    x_param, y_param = 'PosPxX', 'PosPxY'
elif args['wh_type'] == 'degrees':
    x_values, y_values = args['df_sd']['DistDegFromFixPointX'].unique(), args['df_sd']['DistDegFromFixPointY'].unique()
    x_label, y_label = 'Distance from Fixation Point X (°)', 'Distance from Fixation Point Y (°)'
    x_param, y_param = 'DistDegFromFixPointX', 'DistDegFromFixPointY'
else:
    raise ValueError(f'Unknown value of args wh_type key. Try using pixels or degrees.')

# Define threshold, vmin, and vmax based on the specified threshold type
threshold = None
vmin, vmax = None, None

if args['threshold'] == 'brightness':
    threshold = 'Threshold Brightness'
    if args['vmax_vmin'] == 'session_info':
        vmin, vmax = args['stim_bright_range'][0], args['stim_bright_range'][1]
elif args['threshold'] == 'luminance':
    threshold = 'Threshold Luminance'
    if args['vmax_vmin'] == 'session_info':
        vmin, vmax = args['stim_lum_range'][0], args['stim_lum_range'][1]
elif args['threshold'] == 'decibel':
    threshold = 'Threshold Decibel'
    if args['vmax_vmin'] == 'session_info':
        vmin, vmax = args['stim_db_range'][0], args['stim_db_range'][1]
else:
    raise ValueError(
        f'Unknown value of args threshold key. Try using brightness, luminance or decibel.')

# Create a matrix to store the ThresholdDecibel values
matrix = [[None for _ in range(len(x_values))] for _ in range(len(y_values))]

# Fill the matrix with ThresholdDecibel values
for index, row in args['df_sd'].iterrows():
    x_index = list(x_values).index(row[x_param])
    y_index = list(y_values).index(row[y_param])
    matrix[y_index][x_index] = row[threshold.replace(' ', '')]

# Create a figure with specified size
plt.figure(figsize=args['fig_size'])

# Plot based on the specified fig_type
if args['fig_type'] == 'imshow':
    # Plot as an image
    plt.imshow(matrix, cmap=args['cmap'], interpolation='nearest', vmax=vmax, vmin=vmin)
    plt.colorbar(label=threshold)
    plt.xticks(range(len(x_values)), x_values)
    plt.yticks(range(len(y_values)), y_values)

    # Display x and y zero lines. Determine the position of the fixation point, assuming that
    # the fixation point was displayed in the center of the screen.
    plt.axhline(len(y_values) / 2 - 0.5, color='black', linewidth=1)
    plt.axvline(len(x_values) / 2 - 0.5, color='black', linewidth=1)

elif args['fig_type'] == 'contour':
    # Plot as a contour
    matrix = np.asarray(matrix)
    matrix = np.flip(matrix, axis=0)

    if args['contour_interp'] > 0:
        matrix = sn.zoom(matrix, args['contour_interp'])

    cf = plt.contourf(matrix, cmap=args['cmap'], levels=np.linspace(float(vmin), float(vmax), args['contour_levels']), vmin=vmin, vmax=vmax, extend='max')
    plt.colorbar(cf, label=threshold)

    print(vmin, vmax)

    print(np.shape(matrix)[0], np.shape(matrix)[1])

    plt.xticks(np.linspace(0, np.shape(matrix)[1]-1, len(x_values)), x_values)
    plt.yticks(np.linspace(0, np.shape(matrix)[0]-1, len(y_values)), y_values)

    # Display x and y zero lines. Determine the position of the fixation point, assuming that
    # the fixation point was displayed in the center of the screen.
    plt.axhline(np.shape(matrix)[0] / 2, color='black', linewidth=1)
    plt.axvline(np.shape(matrix)[1] / 2, color='black', linewidth=1)

    if args['show_contour_lines']:
        contour = plt.contour(matrix, colors=args['clabel_color'], levels=np.linspace(float(vmin), float(vmax), args['contour_levels']), linewidths=1)
        plt.clabel(contour, inline=True, fontsize=args['clabel_font_size'])

else:
    raise ValueError(f'Unknown value of args fig_type key. Try using imshow or contour.')

# Set x-axis label
plt.xlabel(x_label)

# Set y-axis label
plt.ylabel(y_label)

# Adjust layout to prevent clipping of labels and elements
plt.tight_layout()

# Display the plot
plt.show()
